package com.fit.feast.domain.usecases
interface UseCaseWithParms<in P, R> {
    suspend fun execute(params: P): R
}