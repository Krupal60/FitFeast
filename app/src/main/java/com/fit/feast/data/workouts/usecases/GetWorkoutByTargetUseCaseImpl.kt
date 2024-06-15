package com.fit.feast.data.workouts.usecases

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.GetWorkoutByBodyPartUseCase
import com.fit.feast.domain.usecases.GetWorkoutByTargetUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutByTargetUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) :
    GetWorkoutByTargetUseCase {
    override suspend fun execute(targetMuscle: String): Flow<PagingData<Exercises>> {
        return workoutsRepository.byTargetMuscle(targetMuscle)
    }

}