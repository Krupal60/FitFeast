package com.fit.feast.data.workouts.usecases

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) :
    UseCase<Flow<PagingData<Exercises>>> {
    override suspend fun execute(): Flow<PagingData<Exercises>> {
       return workoutsRepository.getDataAll()
    }
}