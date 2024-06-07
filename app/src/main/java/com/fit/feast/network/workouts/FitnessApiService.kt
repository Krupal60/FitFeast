package com.fit.feast.network.workouts


import com.fit.feast.data.workouts.Exercises
import com.fit.feast.util.FitApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FitnessApiService {

    @GET(FitApiConstants.Exercises)
    suspend fun getExercises(
        @Query("limit") limit : Int,
        @Query("offset") page : Int
    ): Response<List<Exercises>>

    @GET(FitApiConstants.Bodyparts)
    suspend fun getBodyParts(
        @Query("limit") limit : Int
    ): Response<List<String>>

    @GET(FitApiConstants.Exercises)
    suspend fun byBodyParts(
        @Query("limit") limit : Int,
        @Query("offset") page : Int
    ): Response<List<Exercises>>
}