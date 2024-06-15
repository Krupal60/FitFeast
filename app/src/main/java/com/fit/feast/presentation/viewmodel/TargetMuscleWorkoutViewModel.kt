package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.usecases.GetWorkoutByTargetUseCaseImpl
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
class TargetMuscleWorkoutViewModel @Inject constructor(
    private val WorkoutByTargetUseCase: GetWorkoutByTargetUseCaseImpl
) : ViewModel() {
    private val _data = MutableStateFlow<Flow<PagingData<Exercises>>>(value = flowOf(PagingData.empty()))
    val data: StateFlow<Flow<PagingData<Exercises>>> = _data.asStateFlow()


    fun getData(targetMuscle: String) {
        viewModelScope.launch {
            WorkoutByTargetUseCase.execute(targetMuscle).catch { }
                .map { value: PagingData<Exercises> ->
                    value.map {
                        Exercises(
                            name = it.name, id = it.id, bodyPart = it.bodyPart,
                            instructions = it.instructions, secondaryMuscles = it.secondaryMuscles,
                            gifUrl = it.gifUrl, equipment = it.equipment, target = it.target
                        )
                    }

                }.cachedIn(viewModelScope)
        }
    }

}