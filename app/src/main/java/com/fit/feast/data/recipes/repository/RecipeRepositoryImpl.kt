package com.fit.feast.data.recipes.repository

import com.fit.feast.data.recipes.Meal
import com.fit.feast.domain.repository.recipes.RecipeRepository
import com.fit.feast.network.recipes.RecipeApiService
import com.fit.feast.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class RecipeRepositoryImpl(
    private val apiService: RecipeApiService
) : RecipeRepository {
    override suspend fun getMealCategories(): Flow<RequestState<List<Meal>>> {
        return flow {
            try {
                val data = apiService.getCategories()
                if (data.code() == 200 && !data.body().isNullOrEmpty()) {
                    emit(RequestState.Success(data.body()!!))
                } else {
                    emit(RequestState.Error(data.message()))
                }
            } catch (e: HttpException) {
                emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getArea(): Flow<RequestState<List<String>>> {
        return flow {
            try {
                val data = apiService.getArea()
                if (data.code() == 200 && !data.body().isNullOrEmpty()) {
                    emit(RequestState.Success(data.body()!!))
                } else {
                    emit(RequestState.Error(data.message()))
                }
            } catch (e: HttpException) {
                emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(RequestState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }.flowOn(Dispatchers.IO)
    }
}