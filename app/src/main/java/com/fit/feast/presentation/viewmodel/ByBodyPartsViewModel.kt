package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.feast.data.workouts.usecases.GetBodyPartsUseCaseImpl
import com.fit.feast.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ByBodyPartsViewModel @Inject constructor(
    private val getBodyPartsUseCase: GetBodyPartsUseCaseImpl
) : ViewModel() {

    private val _bodyParts = MutableStateFlow<RequestState<List<String>>>(RequestState.Loading)
    val bodyParts: StateFlow<RequestState<List<String>>> = _bodyParts.asStateFlow()

    fun getBodyPartsList() {
        viewModelScope.launch {
            getBodyPartsUseCase.execute()
                .catch { e ->
                    _bodyParts.value = RequestState.Error(e.localizedMessage ?: "Unknown error")
                }
                .collect { requestState ->
                    _bodyParts.value = requestState
                }
        }
    }
}