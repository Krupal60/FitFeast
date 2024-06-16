package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.feast.data.workouts.usecases.GetEquipmentsListUseCaseImpl
import com.fit.feast.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ByEquipmentsViewModel@Inject constructor(
    private val byEquipmentsUseCase: GetEquipmentsListUseCaseImpl
): ViewModel() {

    private val _data = MutableStateFlow<RequestState<List<String>>>(value = RequestState.Loading)
    val data: StateFlow<RequestState<List<String>>> = _data.asStateFlow()

    fun getData() {
        viewModelScope.launch {
            byEquipmentsUseCase.execute().catch {
                _data.value = RequestState.Error(it.localizedMessage ?: "Error")
            }.collect{
                _data.value = it
            }
        }
    }
}