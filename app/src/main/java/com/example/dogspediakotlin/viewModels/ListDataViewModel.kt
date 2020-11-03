package com.example.dogspediakotlin.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.dogspediakotlin.models.DogBreeds
import com.example.dogspediakotlin.models.DogDatabase
import com.example.dogspediakotlin.networkUtils.DogsApiService
import com.example.dogspediakotlin.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListDataViewModel(application: Application):BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreeds>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun generateDummyData(){
        /*val dog1 = DogBreeds("1","Corgi","15 years","breadGroup","breedFor","temperament","")
        val dog2 = DogBreeds("2","Labrador","10 years","breadGroup","breedFor","temperament","")
        val dog3 = DogBreeds("3","RotWailer","20 years","breadGroup","breedFor","temperament","")

        val dogList = arrayListOf<DogBreeds>(dog1,dog2,dog3)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false*/
        fetchRemoteData()
    }

    private fun fetchRemoteData() {
        loading.value = true
        disposable.add(
            dogsApiService.useDogData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreeds>>() {
                    override fun onSuccess(dogList: List<DogBreeds>) {
                        /*dogs.value = dogList
                        dogsLoadError.value = false
                        loading.value = false*/
                        saveDogsDataLocally(dogList)
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun dogsRetrieved(dogList:List<DogBreeds>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun saveDogsDataLocally(list: List<DogBreeds>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())
            var i =0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}