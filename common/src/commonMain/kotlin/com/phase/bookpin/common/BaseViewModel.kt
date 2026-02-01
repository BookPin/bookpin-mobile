package com.phase.bookpin.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, SideEffect> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _sideEffect = Channel<SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    val state get() = uiState.value

    protected fun reduce(action: State.() -> State) {
        _uiState.update(action)
    }

    protected fun postSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(sideEffect)
        }
    }
}
