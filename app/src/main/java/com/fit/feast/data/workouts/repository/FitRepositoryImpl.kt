package com.fit.feast.data.workouts.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fit.feast.data.workouts.pagingsource.ByBodyPartExerciesPagingSource
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.pagingsource.ExercisesPagingSource
import com.fit.feast.domain.FItRepository
import com.fit.feast.network.workouts.FitnessApiService
import com.fit.feast.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FitRepositoryImpl(
    private val apiService: FitnessApiService
) : FItRepository {

    override fun getDataAll(): Flow<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1,
                maxSize = 1300
            ), pagingSourceFactory = {
                ExercisesPagingSource(apiService)
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getBodyParts(): Flow<RequestState<List<String>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                val data = apiService.getBodyParts(15)
                when (data.code()) {
                    200 -> {
                        emit(RequestState.Success(data.body()!!))
                    }

                    else -> {
                        emit(RequestState.Error("Unexpected Error"))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.Error("Unexpected Error"))
            }

        }
    }

    override fun byBodyParts(part: String): Flow<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1,
                maxSize = 1000
            ),
            pagingSourceFactory = {
                ByBodyPartExerciesPagingSource(part, apiService)
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}