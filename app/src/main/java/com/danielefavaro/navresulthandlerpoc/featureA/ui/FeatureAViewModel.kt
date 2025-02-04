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

data class FeatureAState(
    val arg: Int? = null,
    val event: FeatureAEvent = FeatureAEvent.None,
)

sealed class FeatureAEvent {
    data object None : FeatureAEvent()
    data object OnArgConsumed : FeatureAEvent()
}

@HiltViewModel
class FeatureAViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<FeatureAState> = MutableStateFlow(FeatureAState())
    val state: StateFlow<FeatureAState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value
    )

    fun consumeArg(arg: Int?) {
        _state.update {
            it.copy(
                arg = arg,
                event = FeatureAEvent.OnArgConsumed,
            )
        }
    }

    fun onEventConsumed() {
        _state.update {
            it.copy(
                event = FeatureAEvent.None,
            )
        }
    }
}