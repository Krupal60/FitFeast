package com.fit.feast.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import kotlinx.coroutines.flow.Flow

interface GetWorkoutUseCase {
    suspend fun execute(): Flow<PagingData<Exercises>>
}