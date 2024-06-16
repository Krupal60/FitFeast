package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fit.feast.data.workouts.Exercises
import com.fit.feast.data.workouts.usecases.GetWorkoutByEquimentUseCaseImpl
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
class EquipmentWorkoutViewModel @Inject constructor(
    private val useCase : GetWorkoutByEquimentUseCaseImpl
): ViewModel() {

    private val _data = MutableStateFlow<Flow<PagingData<Exercises>>>(value = flowOf(PagingData.empty()))
    val data: StateFlow<Flow<PagingData<Exercises>>> = _data.asStateFlow()

    fun getData(equipment: String?) {
        viewModelScope.launch {
        val flow = useCase.execute(equipment!!).catch {  }.map { value: PagingData<Exercises> ->
                value.map {
                    Exercises(
                        bodyPart = it.bodyPart, equipment = it.equipment,
                        id = it.id, gifUrl = it.gifUrl, target = it.target,
                        instructions = it.instructions,
                        secondaryMuscles = it.secondaryMuscles, name = it.name
                    )
                }
            }.cachedIn(viewModelScope)
            _data.value = flow
        }
    }

}