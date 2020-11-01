package com.example.dogspediakotlin.networkUtils

import com.example.dogspediakotlin.models.DogBreeds
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogsData(): Single<List<DogBreeds>>

}