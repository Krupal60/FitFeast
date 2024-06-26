package com.fit.feast.data.workouts.usecases


import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.domain.usecases.UseCase
import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBodyPartsUseCaseImpl @Inject constructor(private val workoutsRepository: FitRepositoryImpl) :
    UseCase<Flow<RequestState<List<String>>>> {
    override suspend fun execute(): Flow<RequestState<List<String>>> {
        return workoutsRepository.getBodyParts()
    }

}