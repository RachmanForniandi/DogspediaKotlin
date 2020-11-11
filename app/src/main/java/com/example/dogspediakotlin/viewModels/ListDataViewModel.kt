package com.example.dogspediakotlin.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.dogspediakotlin.models.DogBreeds
import com.example.dogspediakotlin.models.DogDatabase
import com.example.dogspediakotlin.networkUtils.DogsApiService
import com.example.dogspediakotlin.utils.NotificationHelper
import com.example.dogspediakotlin.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException


class ListDataViewModel(application: Application):BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 100L
    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreeds>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun generateDummyData(){
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromLocalDatabase()
        }else{
            fetchRemoteData()
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()

        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 *60
            refreshTime = cachePreferenceInt.times(1000 *1000 *1000L)
        }catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }

    fun refreshByPassCache(){
        fetchRemoteData()
    }

    private fun fetchFromLocalDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(),"Dogs retrieved from database",Toast.LENGTH_SHORT).show()
        }
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
                        Toast.makeText(getApplication(),"Dogs retrieved from end point service",Toast.LENGTH_SHORT).show()
                        NotificationHelper(getApplication()).createNotification()
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