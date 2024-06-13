package com.fit.feast.domain.usecases


import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import kotlinx.coroutines.flow.Flow

interface GetWorkoutByBodyPartUseCase {
    suspend fun execute(bodyPart: String): Flow<PagingData<Exercises>>
}