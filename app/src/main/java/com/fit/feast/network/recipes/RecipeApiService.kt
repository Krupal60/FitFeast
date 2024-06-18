package com.fit.feast.network.recipes


import com.fit.feast.data.recipes.Meal
import com.fit.feast.util.RecipeApiConstants
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApiService {

    @GET(RecipeApiConstants.categories)
    suspend fun getCategories(): Response<List<Meal>>


    @GET(RecipeApiConstants.area)
    suspend fun getArea(): Response<List<String>>


}