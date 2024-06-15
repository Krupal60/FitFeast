package com.fit.feast.domain.usecases


import com.fit.feast.util.RequestState
import kotlinx.coroutines.flow.Flow

interface GetTargetListUseCase {
    suspend fun execute(): Flow<RequestState<List<String>>>
}