package com.fit.feast.domain.usecases


import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow

interface GetBodyPartsUseCase {
    suspend fun execute(): Flow<RequestState<List<String>>>
}