package com.z3r08ug.vaxcareinterview.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewState
interface ViewIntent
interface ViewSideEffect

abstract class BaseViewModel<S : ViewState, I : ViewIntent, E : ViewSideEffect> : ViewModel() {
    private val initialState: S by lazy { createInitialState() }
    abstract fun createInitialState(): S

    private val _currentState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val currentState: StateFlow<S> = _currentState.asStateFlow()

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()
    val intent: SharedFlow<I> = _intent.asSharedFlow()

    private val _effect: Channel<E> = Channel()
    val effect: Flow<E> = _effect.receiveAsFlow()

    init {
        subscribeIntents()
    }

    private fun subscribeIntents() {
        viewModelScope.launch {
            _intent.collect {
                handleIntent(it)
            }
        }
    }

    abstract fun handleIntent(intent: I)

    fun setIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    protected fun setState(reduce: S.() -> S) {
        val newState = currentState.value.reduce()
        _currentState.value = newState
    }

    protected fun setEffect(builder: () -> E) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }
}
