package com.fit.feast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.feast.data.workouts.usecases.GetTargetListUseCaseImpl
import com.fit.feast.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ByTargetsViewModel@Inject constructor( val getTargetsUseCase: GetTargetListUseCaseImpl): ViewModel() {

    private val _data = MutableStateFlow<RequestState<List<String>>>(value = RequestState.Loading)
    val data : StateFlow<RequestState<List<String>>> get() = _data.asStateFlow()
    fun getData(){
        viewModelScope.launch {
            getTargetsUseCase.execute().catch {
                _data.value = RequestState.Error(it.localizedMessage!!)
            }.map { value: RequestState<List<String>> ->
                _data.value = RequestState.Success(value.getSuccessDataOrNull()!!)
            }
        }
    }
}