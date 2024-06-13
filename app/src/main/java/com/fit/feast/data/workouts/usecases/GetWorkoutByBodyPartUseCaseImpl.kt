package com.fit.feast.data.workouts.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.GetWorkoutByBodyPartUseCase
import com.fit.feast.domain.usecases.GetWorkoutUseCase
import javax.inject.Inject

class GetWorkoutByBodyPartUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) : GetWorkoutByBodyPartUseCase {
    override suspend fun execute(bodyPart: String): LiveData<PagingData<Exercises>> {
        return workoutsRepository.byBodyParts(bodyPart)
    }

}