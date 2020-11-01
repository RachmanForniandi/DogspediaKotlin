package com.example.dogspediakotlin.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogspediakotlin.models.DogBreeds

class ListDataViewModel:ViewModel() {

    val dogs = MutableLiveData<List<DogBreeds>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun generateDummyData(){
        val dog1 = DogBreeds("1","Corgi","15 years","breadGroup","breedFor","temperament","")
        val dog2 = DogBreeds("2","Labrador","10 years","breadGroup","breedFor","temperament","")
        val dog3 = DogBreeds("3","RotWailer","20 years","breadGroup","breedFor","temperament","")

        val dogList = arrayListOf<DogBreeds>(dog1,dog2,dog3)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}