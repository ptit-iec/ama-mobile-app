package com.iec.makeup.ui.features.home.screen_all_makeup

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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class AllMakeUpStylistState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<Expert> = emptyList()
) : Reducer.ViewState

sealed class AllMakeUpStylistEffect : Reducer.ViewEffect {
    data class ShowToast(val message: String) : AllMakeUpStylistEffect()
    data class ShowError(val message: String?) : AllMakeUpStylistEffect()
}

sealed class AllMakeUpStylistEvent : Reducer.ViewEvent {
    data class OnInitData(
        val data: List<Expert>
    ) : AllMakeUpStylistEvent()

    data class OnLoading(
        val isLoading: Boolean
    ) : AllMakeUpStylistEvent()

    data class OnError(
        val error: String?
    ) : AllMakeUpStylistEvent()

    data class OnLoadMore(
        val data: List<Expert>
    ) : AllMakeUpStylistEvent()
}

class AllMakeUpStylistReducer :
    Reducer<AllMakeUpStylistState, AllMakeUpStylistEvent, AllMakeUpStylistEffect> {
    override fun reduce(
        currentState: AllMakeUpStylistState,
        event: AllMakeUpStylistEvent
    ): Pair<AllMakeUpStylistState, AllMakeUpStylistEffect?> {
        return when (event) {
            is AllMakeUpStylistEvent.OnInitData -> {
                currentState.copy(
                    data = event.data
                ) to null
            }

            is AllMakeUpStylistEvent.OnLoading -> {
                currentState.copy(
                    isLoading = event.isLoading
                ) to null
            }

            is AllMakeUpStylistEvent.OnError -> {
                currentState.copy(
                    error = event.error
                ) to AllMakeUpStylistEffect.ShowError(event.error)
            }

            is AllMakeUpStylistEvent.OnLoadMore -> {
                currentState.copy(
                    data = currentState.data + event.data
                ) to null
            }
        }
    }

}

@HiltViewModel
class AllMakeUpVM @Inject constructor(
    private val expertRepository: ExpertRepository
) : BaseViewModel<AllMakeUpStylistState, AllMakeUpStylistEvent, AllMakeUpStylistEffect>(
    initialState = AllMakeUpStylistState(),
    reducer = AllMakeUpStylistReducer()
) {
    private var isFetchingData = false
    init {
        sendEvent(AllMakeUpStylistEvent.OnLoading(true))
        getExperts(true).onEach {
            sendEvent(AllMakeUpStylistEvent.OnInitData(it))
            sendEvent(AllMakeUpStylistEvent.OnLoading(false))
        }.catch {
            sendEventWithEffect(AllMakeUpStylistEvent.OnError(it.message))
        }.launchIn(viewModelScope)
    }
    fun loadMoreExperts(){
        if(!isFetchingData){
            isFetchingData = true
            sendEvent(AllMakeUpStylistEvent.OnLoading(true))
            getExperts(false).onEach {
                sendEvent(AllMakeUpStylistEvent.OnLoadMore(it))
                sendEvent(AllMakeUpStylistEvent.OnLoading(false))
                isFetchingData = false
            }.catch {
                sendEventWithEffect(AllMakeUpStylistEvent.OnError(it.message))
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