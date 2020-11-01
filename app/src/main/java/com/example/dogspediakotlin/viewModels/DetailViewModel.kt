package com.example.dogspediakotlin.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogspediakotlin.models.DogBreeds

class DetailViewModel :ViewModel(){

    val dogLiveData = MutableLiveData<DogBreeds>()

    fun fetch(){
        val dog = DogBreeds("1","Corgi","15 years","breadGroup","breedFor","temperament","")
        dogLiveData.value = dog
    }
}