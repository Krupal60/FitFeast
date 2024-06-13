package com.fit.feast.data.workouts.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.GetBodyPartsUseCase
import com.fit.feast.domain.usecases.GetWorkoutUseCase
import com.fit.feast.util.RequestState
import javax.inject.Inject

class GetBodyPartsUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) : GetBodyPartsUseCase {
    override suspend fun execute(): LiveData<RequestState<List<String>>> {
        return workoutsRepository.getBodyParts()
    }

}