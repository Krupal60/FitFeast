package com.fit.feast.data.workouts.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fit.feast.data.workouts.ByBodyPartExerciesPagingSource
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.ExercisesPagingSource
import com.fit.feast.domain.FItRepository
import com.fit.feast.network.workouts.FitnessApiService
import com.fit.feast.util.RequestState
import javax.inject.Inject

class FitRepositoryImpl @Inject constructor(
    private val apiService: FitnessApiService
) : FItRepository {

    override fun getDataAll(): LiveData<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1,
                maxSize = 1300
            ), pagingSourceFactory = {
                ExercisesPagingSource(apiService)
            }
        ).liveData
    }

    override fun getBodyParts(): LiveData<RequestState<List<String>>> {
        return liveData {
            emit(RequestState.Loading)
            try {
                val data = apiService.getBodyParts(15)
                when (data.code()) {
                    200 -> {
                        emit(RequestState.Success(data.body()!!))
                    }

                    else -> {
                        emit(RequestState.Error("Unexpected Error"))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.Error("Unexpected Error"))
            }

        }
    }

    override fun byBodyParts(part: String): LiveData<PagingData<Exercises>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 1,
                maxSize = 1000
            ),
            pagingSourceFactory = {
                ByBodyPartExerciesPagingSource(part, apiService)
            }
        ).liveData
    }
}