package com.fit.feast.data.workouts.usecases

import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.UseCaseWithParms
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutByNameUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) :
    UseCaseWithParms<String, Flow<PagingData<Exercises>>> {
    override suspend fun execute(params: String): Flow<PagingData<Exercises>> {
        return workoutsRepository.getDataByName(params)
    }

}