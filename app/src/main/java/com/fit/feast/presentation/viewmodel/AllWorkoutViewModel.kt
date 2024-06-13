package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.usecases.GetWorkoutUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllWorkoutViewModel @Inject constructor(private val getWorkoutUseCaseImpl: GetWorkoutUseCaseImpl) :
    ViewModel() {

    private val _allWorkout = MutableStateFlow<Flow<PagingData<Exercises>>>(
        value = flowOf(PagingData.empty())
    )
    val allWorkout: StateFlow<Flow<PagingData<Exercises>>>
        get() = _allWorkout.asStateFlow()

    fun getWorkout() {
        viewModelScope.launch {
            val flow = getWorkoutUseCaseImpl.execute().catch { }.map { pagingData ->
                pagingData.map {
                    Exercises(
                        bodyPart = it.bodyPart, equipment = it.equipment,
                        id = it.id, gifUrl = it.gifUrl, target = it.target,
                        instructions = it.instructions,
                        secondaryMuscles = it.secondaryMuscles, name = it.name
                    )
                }
            }.cachedIn(viewModelScope)
            _allWorkout.value = flow
        }
    }

}