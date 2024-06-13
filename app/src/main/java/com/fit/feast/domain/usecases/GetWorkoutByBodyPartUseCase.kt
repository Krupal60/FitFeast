package com.fit.feast.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises

interface GetWorkoutByBodyPartUseCase {
    suspend fun execute(bodyPart: String): LiveData<PagingData<Exercises>>
}