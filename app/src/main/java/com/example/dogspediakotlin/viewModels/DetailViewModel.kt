package com.example.dogspediakotlin.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogspediakotlin.models.DogBreeds
import com.example.dogspediakotlin.models.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) :BaseViewModel(application){

    val dogLiveData = MutableLiveData<DogBreeds>()

    fun fetch(uuid:Int){
        launch {
            val dog = DogDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }

    }
}