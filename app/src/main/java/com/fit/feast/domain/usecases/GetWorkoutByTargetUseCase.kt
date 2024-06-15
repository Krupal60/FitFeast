package com.fit.feast.domain.usecases


import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import kotlinx.coroutines.flow.Flow

interface GetWorkoutByTargetUseCase {
    suspend fun execute(targetMuscle: String): Flow<PagingData<Exercises>>
}