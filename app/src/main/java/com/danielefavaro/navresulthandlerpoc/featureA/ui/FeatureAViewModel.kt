package com.danielefavaro.navresulthandlerpoc.featureA.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class FeatureAViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<String> = MutableStateFlow<String>("Empty result")
    val state: StateFlow<String> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value
    )

    fun onNewState(value: String) {
        _state.update { value }
    }
}