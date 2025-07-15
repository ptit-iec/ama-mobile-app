package com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.core.model.ui.mockMakeUpTemplateLayout
import com.iec.makeup.data.remote.dto.toMakeUpTemplate
import com.iec.makeup.data.repository.MakeUpTemplateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ScreenAllMakeUpTemplateViewState(
    val isLoading: Boolean = false,
    val data: List<MakeUpTemplateLayout> = listOf(),
    val error: String = ""
) : Reducer.ViewState


sealed class ScreenAllMakeUpTemplateEvent() : Reducer.ViewEvent {
    data class Loading(val isLoading: Boolean) : ScreenAllMakeUpTemplateEvent()
    data class Success(val data: List<MakeUpTemplateLayout>) : ScreenAllMakeUpTemplateEvent()
    data class Error(val error: String) : ScreenAllMakeUpTemplateEvent()
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
            is ScreenAllMakeUpTemplateEvent.Loading -> currState.copy(isLoading = event.isLoading) to ScreenAllMakeUpTemplateEffect.Loading
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
class ScreenAllMakeupTemplateVM @Inject constructor(
    private val makeUpTemplateRepository: MakeUpTemplateRepository
) :
    BaseViewModel<ScreenAllMakeUpTemplateViewState, ScreenAllMakeUpTemplateEvent, ScreenAllMakeUpTemplateEffect>(
        initialState = ScreenAllMakeUpTemplateViewState(),
        reducer = ScreenAllMakeUpTemplateReducer()
    ) {
    private fun getMakeUpTemplate(id: String) = flow {
        val makeUpTemplate = makeUpTemplateRepository.getMakeUpTemplateById(id)?.toMakeUpTemplate()
        Log.d("ScreenAllMakeupTemplateVM", "getMakeUpTemplate: $makeUpTemplate")
        emit(makeUpTemplate)
    }

    fun getInitialMakeUpTemplate(ids: List<String>) {
        sendEvent(ScreenAllMakeUpTemplateEvent.Loading(true))
        if(ids.isEmpty()){
            sendEvent(ScreenAllMakeUpTemplateEvent.Loading(false))
            sendEvent(ScreenAllMakeUpTemplateEvent.Success(emptyList()))
            return
        }
        val listData = ids.map { getMakeUpTemplate(it) }
        combine(listData){ it ->
            sendEvent(ScreenAllMakeUpTemplateEvent.Success(it.filterNotNull()))
        }.launchIn(viewModelScope)
    }
}