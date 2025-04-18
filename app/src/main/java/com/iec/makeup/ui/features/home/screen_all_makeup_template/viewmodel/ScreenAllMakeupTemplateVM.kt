package com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel

import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.core.model.ui.mockMakeUpTemplateLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ScreenAllMakeUpTemplateViewState(
    val isLoading: Boolean = false,
    val data: List<MakeUpTemplateLayout> = listOf(),
    val error: String = ""
) : Reducer.ViewState


sealed class ScreenAllMakeUpTemplateEvent() : Reducer.ViewEvent {
    data object Loading : ScreenAllMakeUpTemplateEvent()
    class Success(val data: List<MakeUpTemplateLayout>) : ScreenAllMakeUpTemplateEvent()
    class Error(val error: String) : ScreenAllMakeUpTemplateEvent()
}

sealed class ScreenAllMakeUpTemplateEffect : Reducer.ViewEffect {
    data object Loading : ScreenAllMakeUpTemplateEffect()
    class Error(val error: String) : ScreenAllMakeUpTemplateEffect()
}

class ScreenAllMakeUpTemplateReducer :
    Reducer<ScreenAllMakeUpTemplateViewState, ScreenAllMakeUpTemplateEvent, ScreenAllMakeUpTemplateEffect> {
    override fun reduce(
        currState: ScreenAllMakeUpTemplateViewState,
        event: ScreenAllMakeUpTemplateEvent
    ): Pair<ScreenAllMakeUpTemplateViewState, ScreenAllMakeUpTemplateEffect?> {
        return when (event) {
            is ScreenAllMakeUpTemplateEvent.Loading -> currState.copy(isLoading = true) to ScreenAllMakeUpTemplateEffect.Loading
            is ScreenAllMakeUpTemplateEvent.Success -> currState.copy(
                isLoading = false,
                data = event.data
            ) to ScreenAllMakeUpTemplateEffect.Loading

            is ScreenAllMakeUpTemplateEvent.Error -> currState.copy(error = event.error) to ScreenAllMakeUpTemplateEffect.Error(
                event.error
            )
        }
    }
}

@HiltViewModel
class ScreenAllMakeupTemplateVM @Inject constructor() :
    BaseViewModel<ScreenAllMakeUpTemplateViewState, ScreenAllMakeUpTemplateEvent, ScreenAllMakeUpTemplateEffect>(
        initialState = ScreenAllMakeUpTemplateViewState(),
        reducer = ScreenAllMakeUpTemplateReducer()
    ) {
    init {
        sendEventWithEffect(ScreenAllMakeUpTemplateEvent.Loading)
        viewModelScope.launch {
            delay(2000)
            val dataArrive = mockMakeUpTemplateLayout
            sendEventWithEffect(ScreenAllMakeUpTemplateEvent.Success(dataArrive))

        }
    }
}