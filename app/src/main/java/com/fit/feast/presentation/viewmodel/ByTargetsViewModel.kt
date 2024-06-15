package com.fit.feast.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.feast.data.workouts.usecases.GetTargetListUseCaseImpl
import com.fit.feast.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ByTargetsViewModel@Inject constructor( private val getTargetsUseCase: GetTargetListUseCaseImpl): ViewModel() {

    private val _data = MutableStateFlow<RequestState<List<String>>>(value = RequestState.Loading)
    val data : StateFlow<RequestState<List<String>>> get() = _data.asStateFlow()
    fun getData(){
        viewModelScope.launch {
            getTargetsUseCase.execute().catch {
                _data.value = RequestState.Error(it.localizedMessage ?: "Error")
                Log.d("TAG", "error")
            }.collect { value: RequestState<List<String>> ->
                Log.d("TAG", "getData: $value")
                _data.value = value
            }
        }
    }
}