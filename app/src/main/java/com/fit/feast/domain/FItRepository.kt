package com.fit.feast.domain

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow


interface FItRepository {
    fun getDataAll(): Flow<PagingData<Exercises>>
    fun getBodyParts(): Flow<RequestState<List<String>>>
    fun byBodyParts(bodyPart: String): Flow<PagingData<Exercises>>
}