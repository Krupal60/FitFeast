package com.fit.feast.data.workouts.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.GetWorkoutUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) : GetWorkoutUseCase {
    override suspend fun execute(): Flow<PagingData<Exercises>> {
       return workoutsRepository.getDataAll()
    }
}