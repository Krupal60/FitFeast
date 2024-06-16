package com.fit.feast.data.workouts.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.pagingsource.ByBodyPartExerciesPagingSource
import com.fit.feast.data.workouts.pagingsource.ByEquipmentExerciesPagingSource
import com.fit.feast.data.workouts.pagingsource.ByTargetMuscleExerciesPagingSource
import com.fit.feast.data.workouts.pagingsource.ExercisesPagingSource
import com.fit.feast.domain.repository.FItRepository
import com.fit.feast.network.workouts.FitnessApiService
import com.fit.feast.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FitRepositoryImpl(
    private val apiService: FitnessApiService
) : FItRepository {

    override suspend fun getDataAll(): Flow<PagingData<Exercises>> {
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

    override suspend fun getBodyParts(): Flow<RequestState<List<String>>> {
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

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTargetList(): Flow<RequestState<List<String>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                val data = apiService.getTargetList()
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

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getEquipmentsList(): Flow<RequestState<List<String>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                val data = apiService.getEquipmentList()
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

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun byBodyParts(bodyPart: String): Flow<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1
            ), pagingSourceFactory = {
            ByBodyPartExerciesPagingSource(bodyPart,apiService)
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun byEquipment(equipment: String): Flow<PagingData<Exercises>> {
        return Pager(config = PagingConfig(
            pageSize = 1,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = 1
        ), pagingSourceFactory = {
            ByEquipmentExerciesPagingSource(equipment,apiService)
        }).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun byTargetMuscle(targetMuscle: String): Flow<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1
            ), pagingSourceFactory = {
                ByTargetMuscleExerciesPagingSource(targetMuscle,apiService)
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}