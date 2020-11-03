package com.example.dogspediakotlin.networkUtils

import com.example.dogspediakotlin.models.DogBreeds
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {

    private val BASE_URL = "https://raw.githubusercontent.com"


    var dogService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogsApi::class.java)

    fun useDogData(): Single<List<DogBreeds>> {
        return dogService.getDogsData()
    }
}