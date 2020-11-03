package com.example.dogspediakotlin.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs:DogBreeds):List<Long>

    @Query("SELECT * FROM dogbreeds")
    suspend fun getAllDogs():List<DogBreeds>

    @Query("SELECT * FROM dogbreeds WHERE uuid =:dogId")
    suspend fun getDog(dogId: Int)

    @Query("DELETE FROM dogbreeds")
    suspend fun deleteAllDogs()
}