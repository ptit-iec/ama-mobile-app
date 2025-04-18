package com.iec.makeup.core

import androidx.lifecycle.ViewModel
import com.iec.makeup.ui.MakeupAppState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow


abstract class BaseViewModel<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect>(
    initialState: State,
    private val reducer: Reducer<State, Event, Effect>,
) : ViewModel() {


    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel(Channel.CONFLATED)
    val effect = _effect.receiveAsFlow()


    init {
        _state.tryEmit(initialState)
    }

    private fun sendEffect(effect: Effect) {
        _effect.trySend(effect)
    }

    fun sendEvent(event: Event) {
        val (newState, _) = reducer.reduce(
            currentState = _state.value,
            event = event
        )
        _state.tryEmit(newState)
    }

    fun sendEventWithEffect(event: Event) {
        val (newState, effect) = reducer.reduce(_state.value, event)

        val success = _state.tryEmit(newState)
        if (success) {
            effect?.let {
                sendEffect(it)
            }
        }
    }


}