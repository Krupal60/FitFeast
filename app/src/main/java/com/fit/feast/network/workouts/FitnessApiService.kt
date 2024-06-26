package com.fit.feast.network.workouts


import com.fit.feast.data.workouts.Exercises
import com.fit.feast.util.FitApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET
    suspend fun byBodyParts(
        @Url url : String,
        @Query("limit") limit : Int,
        @Query("offset") page : Int
    ): Response<List<Exercises>>

    @GET(FitApiConstants.targetList)
    suspend fun getTargetList(): Response<List<String>>
    @GET
    suspend fun byTargetMuscle(
        @Url url : String,
        @Query("limit") limit : Int,
        @Query("offset") page : Int
    ): Response<List<Exercises>>

    @GET(FitApiConstants.equipmentList)
    suspend fun getEquipmentList(): Response<List<String>>
    @GET("${FitApiConstants.name}{name}")
    suspend fun byName(
        @Path ("name") name : String,
        @Query("limit") limit : Int,
        @Query("offset") page : Int
    ): Response<List<Exercises>>

}