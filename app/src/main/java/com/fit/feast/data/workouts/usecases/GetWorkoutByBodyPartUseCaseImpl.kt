package com.fit.feast.data.workouts.usecases

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.GetWorkoutByBodyPartUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutByBodyPartUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) :
    GetWorkoutByBodyPartUseCase {
    override suspend fun execute(bodyPart: String): Flow<PagingData<Exercises>> {
        return workoutsRepository.byBodyParts(bodyPart)
    }

}