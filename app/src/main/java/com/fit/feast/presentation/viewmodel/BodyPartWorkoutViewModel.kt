package com.fit.feast.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.usecases.GetWorkoutByBodyPartUseCaseImpl
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
class BodyPartWorkoutViewModel@Inject constructor(private val getWorkoutByBodyPartUseCase: GetWorkoutByBodyPartUseCaseImpl): ViewModel() {

    private val _data = MutableStateFlow<Flow<PagingData<Exercises>>>(value = flowOf(PagingData.empty()))
    val data : StateFlow<Flow<PagingData<Exercises>>> get() = _data.asStateFlow()

    fun getData(bodyPart: String) {
        viewModelScope.launch {
            val flow =  getWorkoutByBodyPartUseCase.execute(bodyPart).catch {
               Log.e("error", it.message.toString())
            }.map {value: PagingData<Exercises> ->
                value.map {
                    Exercises(name = it.name, id = it.id , bodyPart = it.bodyPart,
                        instructions = it.instructions, secondaryMuscles = it.secondaryMuscles,
                        gifUrl = it.gifUrl, equipment = it.equipment, target = it.target)
                }
            }.cachedIn(viewModelScope)
            _data.value = flow
        }
    }
}