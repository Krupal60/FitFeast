package com.fit.feast.domain.repository

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow


interface FItRepository {
    suspend fun getDataAll(): Flow<PagingData<Exercises>>
    suspend fun getBodyParts(): Flow<RequestState<List<String>>>
    suspend fun getTargetList(): Flow<RequestState<List<String>>>
    suspend fun byBodyParts(bodyPart: String): Flow<PagingData<Exercises>>
    suspend fun byTargetMuscle(targetMuscle: String): Flow<PagingData<Exercises>>
}