package com.iec.makeup.ui.features.ai_makeup.screen_response_ai

import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.data.remote.api.ChatbotEndpoint
import com.iec.makeup.data.remote.api.ChatbotRequest
import com.iec.makeup.data.remote.dto.AIResponses
import com.iec.makeup.data.remote.dto.FaceAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.Serializable
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit


@Serializable
data class InteractionState(
    val isLoading: Boolean = false,
    val currentPickLayout: Int = -1,
    val data: List<String> = emptyList(),
    val error: String? = null,
    val faceAnalysis: FaceAnalysis? = null,
    val resultChatID: String? = null
) : Reducer.ViewState


sealed class InteractionEvent : Reducer.ViewEvent {
    data class OnChooseLayout(val imageIndex: Int) : InteractionEvent()
    data class OnLoading(val isLoading: Boolean) : InteractionEvent()
    data class OnShowError(val message: String?) : InteractionEvent()
    data class OnData(val aiResponses: AIResponses) : InteractionEvent()
}

sealed class InteractionEffect : Reducer.ViewEffect {
    data class ShowError(val message: String?) : InteractionEffect()
}

class InteractionReducer : Reducer<InteractionState, InteractionEvent, InteractionEffect> {
    override fun reduce(currentState: InteractionState, event: InteractionEvent):
            Pair<InteractionState, InteractionEffect?> {
        return when (event) {
            is InteractionEvent.OnChooseLayout -> currentState.copy(currentPickLayout = event.imageIndex) to null
            is InteractionEvent.OnData -> {
                currentState.copy(
                    data = event.aiResponses.makeupImages.map { it.url ?: "" },
                    faceAnalysis = event.aiResponses.faceAnalysis,
                    resultChatID = event.aiResponses.resultChatBotID
                ) to null
            }
            is InteractionEvent.OnLoading -> {
                currentState.copy(isLoading = event.isLoading) to null
            }
            is InteractionEvent.OnShowError -> {
                currentState.copy(error = event.message, isLoading = false) to InteractionEffect.ShowError(event.message)
            }
        }
    }
}

@HiltViewModel
class InteractionVM @Inject constructor(
    private val chatbotEndpoint: ChatbotEndpoint
) :
    BaseViewModel<InteractionState, InteractionEvent, InteractionEffect>(
        initialState = InteractionState(),
        reducer = InteractionReducer()
    ) {

        @OptIn(FlowPreview::class)
        fun getInitResponse(chatbotRequest: ChatbotRequest){
            sendEvent(InteractionEvent.OnLoading(true))
            initResponse(chatbotRequest)
                .timeout((50_000).milliseconds)
                .catch { e ->
                    sendEventWithEffect(InteractionEvent.OnShowError(e.message))
                }
                .onEach {
                    sendEvent(InteractionEvent.OnData(it))
                    sendEvent(InteractionEvent.OnLoading(false))
                }.launchIn(viewModelScope)
        }

        private fun initResponse(
            chatbotRequest: ChatbotRequest
        ) = callbackFlow{
            val result = chatbotEndpoint.getInitAnswer(chatbotRequest)
            if(result.success == true){
                result.data?.aiResponse?.let {
                    trySend(it)
                }
            }else{
            }
            awaitClose {

            }
        }

}