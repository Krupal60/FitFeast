package com.fit.feast.domain.repository.recipes

import com.fit.feast.data.recipes.Meal
import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getMealCategories(): Flow<RequestState<List<Meal>>>
    suspend fun getArea(): Flow<RequestState<List<String>>>

}