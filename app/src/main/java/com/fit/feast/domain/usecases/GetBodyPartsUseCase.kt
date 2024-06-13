package com.fit.feast.domain.usecases

import androidx.lifecycle.LiveData

import com.fit.feast.util.RequestState

interface GetBodyPartsUseCase {
    suspend fun execute(): LiveData<RequestState<List<String>>>
}