package com.iec.makeup.ui.features.ai_makeup.screen_experts_recommend

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.repository.ExpertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class ScreenExpertsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<Expert> = emptyList()
) : Reducer.ViewState

sealed class ScreenExpertsEffect : Reducer.ViewEffect {
    data class ShowToast(val message: String) : ScreenExpertsEffect()
    data class ShowError(val message: String?) : ScreenExpertsEffect()
}

sealed class ScreenExpertsEvent : Reducer.ViewEvent {
    data class OnInitData(
        val data: List<Expert>
    ) : ScreenExpertsEvent()

    data class OnLoading(
        val isLoading: Boolean
    ) : ScreenExpertsEvent()

    data class OnError(
        val error: String?
    ) : ScreenExpertsEvent()

    data class OnLoadMore(
        val data: List<Expert>
    ) : ScreenExpertsEvent()
}

class ScreenExpertsReducer :
    Reducer<ScreenExpertsState, ScreenExpertsEvent, ScreenExpertsEffect> {
    override fun reduce(
        currentState: ScreenExpertsState,
        event: ScreenExpertsEvent
    ): Pair<ScreenExpertsState, ScreenExpertsEffect?> {
        return when (event) {
            is ScreenExpertsEvent.OnInitData -> {
                currentState.copy(
                    data = event.data
                ) to null
            }

            is ScreenExpertsEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading
                ) to null
            }

            is ScreenExpertsEvent.OnError -> {
                currentState.copy(
                    error = event.error
                ) to ScreenExpertsEffect.ShowError(event.error)
            }

            is ScreenExpertsEvent.OnLoadMore -> {
                currentState.copy(
                    data = currentState.data + event.data
                ) to null
            }
        }
    }

}

@HiltViewModel
class ScreenExpertsRcmVM @Inject constructor(
    private val expertRepository: ExpertRepository
) : BaseViewModel<ScreenExpertsState, ScreenExpertsEvent, ScreenExpertsEffect>(
    initialState = ScreenExpertsState(),
    reducer = ScreenExpertsReducer()
) {
    private var isFetchingData = false
    init {
        sendEvent(ScreenExpertsEvent.OnLoading(true))
        getExperts(true).onEach {
            sendEvent(ScreenExpertsEvent.OnInitData(it))
            sendEvent(ScreenExpertsEvent.OnLoading(false))
        }.catch {
            sendEventWithEffect(ScreenExpertsEvent.OnError(it.message))
        }.launchIn(viewModelScope)
    }
    fun loadMoreExperts(){
        if(!isFetchingData){
            isFetchingData = true
            sendEvent(ScreenExpertsEvent.OnLoading(true))
            getExperts(false).onEach {
                sendEvent(ScreenExpertsEvent.OnLoadMore(it))
                sendEvent(ScreenExpertsEvent.OnLoading(false))
                isFetchingData = false
            }.catch {
                sendEventWithEffect(ScreenExpertsEvent.OnError(it.message))
            }.launchIn(viewModelScope)
        }
    }
    private fun getExperts(firstCall: Boolean) = callbackFlow {
        try {
            val list = expertRepository.getAllExperts(
                isFirsTimeCall = firstCall
            )
            trySend(list)
        } catch (e: Exception) {
            Log.d("TAG", "getExperts: ${e.message}")
        }
        awaitClose {

        }
    }
}