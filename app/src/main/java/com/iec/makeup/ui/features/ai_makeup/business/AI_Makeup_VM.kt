package com.iec.makeup.ui.features.ai_makeup.business

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AIScreenState(
    val isLoading: Boolean = false,
    val imageURL: Uri? = null,
    val requestDescription: String? = null,
    val randomList: List<String>? = null
) : Reducer.ViewState

sealed class AIScreenEvent() : Reducer.ViewEvent {
    data class OnUploadImage(val uri: Uri) : AIScreenEvent()
    data class OnCaptureImage(val uri: Uri) : AIScreenEvent()
    data object OnDeleteImage : AIScreenEvent()
    data class OnRequestDescription(val description: String) : AIScreenEvent()
    data object OnSubmit : AIScreenEvent()
    data class OnLoading(val isLoading: Boolean) : AIScreenEvent()
    data class OnError(val message: String?) : AIScreenEvent()
    data class OnInitData(val data: List<String>?, val initPrompts: String? = null) :
        AIScreenEvent()
}


sealed class AIScreenEffect() : Reducer.ViewEffect {
    data class ShowError(val message: String) : AIScreenEffect()
    data class ShowToast(val message: String) : AIScreenEffect()

}

class AIScreenReducer() : Reducer<AIScreenState, AIScreenEvent, AIScreenEffect> {
    override fun reduce(
        currentState: AIScreenState,
        event: AIScreenEvent
    ): Pair<AIScreenState, AIScreenEffect?> {
        return when (event) {
            is AIScreenEvent.OnUploadImage -> {
                currentState.copy(imageURL = event.uri) to null
            }

            is AIScreenEvent.OnCaptureImage -> {
                currentState.copy(imageURL = event.uri) to null
            }

            AIScreenEvent.OnDeleteImage -> {
                currentState.copy(imageURL = null) to null
            }

            is AIScreenEvent.OnRequestDescription -> {
                currentState.copy(requestDescription = event.description) to null
            }

            is AIScreenEvent.OnLoading -> {
                currentState.copy(isLoading = event.isLoading) to null
            }

            AIScreenEvent.OnSubmit -> {
                currentState to null
            }

            is AIScreenEvent.OnError -> {
                currentState to AIScreenEffect.ShowError(event.message ?: "Unknown error")
            }

            is AIScreenEvent.OnInitData -> {
                currentState.copy(
                    randomList = event.data,
                    requestDescription = event.initPrompts
                ) to null
            }
        }
    }

}


@HiltViewModel
class AIScreenVM @Inject constructor() :
    BaseViewModel<AIScreenState, AIScreenEvent, AIScreenEffect>(
        initialState = AIScreenState(),
        reducer = AIScreenReducer()
    ) {


    init {
        viewModelScope.launch {
        }
    }

    fun showLoadingDialog() {
        sendEvent(AIScreenEvent.OnLoading(true))
    }

    fun hideLoadingDialog() {
        sendEvent(AIScreenEvent.OnLoading(false))
    }

    fun showError(message: String?) {
        sendEventWithEffect(AIScreenEvent.OnError(message))
    }

    fun uploadImage(uri: Uri) {
        sendEvent(AIScreenEvent.OnUploadImage(uri))
    }

    fun captureImage(uri: Uri) {
        sendEvent(AIScreenEvent.OnCaptureImage(uri))
    }

    fun onDeleteImage() {
        sendEvent(AIScreenEvent.OnDeleteImage)
    }

    fun inputDescription(description: String) {
        sendEvent(AIScreenEvent.OnRequestDescription(description))
    }

    fun onInitData(data: List<String>?, description: String) {
        sendEvent(AIScreenEvent.OnInitData(data, description))
    }
}