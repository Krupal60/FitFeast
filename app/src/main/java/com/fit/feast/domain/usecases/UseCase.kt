package com.fit.feast.domain.usecases
interface UseCase<R> {
    suspend fun execute(): R
}