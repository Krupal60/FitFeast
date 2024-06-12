package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllWorkoutViewModel @Inject constructor(private val repository: FitRepositoryImpl) :
    ViewModel() {

    private lateinit var _allWorkout: LiveData<PagingData<Exercises>>
    val allWorkout: LiveData<PagingData<Exercises>>
        get() = _allWorkout

    fun getWorkout() {
        _allWorkout = repository.getDataAll()
    }

}