package com.fit.feast.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.util.RequestState


interface FItRepository {
    fun getDataAll(): LiveData<PagingData<Exercises>>
    fun getBodyParts(): LiveData<RequestState<List<String>>>
    fun byBodyParts(bodyPart: String): LiveData<PagingData<Exercises>>
}