package com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai

import androidx.compose.runtime.internal.StabilityInferred
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.HEADER
import com.iec.makeup.core.model.Message
import com.iec.makeup.data.remote.api.ChatbotEndpoint
import com.iec.makeup.data.remote.api.InitMessageRequest
import com.iec.makeup.data.remote.api.MessageRequest
import com.iec.makeup.data.remote.api.MessageRequestTopWrapper
import com.iec.makeup.data.remote.dto.BotMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
data class ScreenChatWithAIState(
    val isLoading: Boolean = false,
    val isBotTyping: Boolean = false,
    val conversationID: String = "",
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val inputChat: String? = null,
    val instruction: String? = null
    ) : Reducer.ViewState

sealed class ScreenChatWithAIEvent : Reducer.ViewEvent {
    data class OnLoading(val isLoading: Boolean) : ScreenChatWithAIEvent()
    data class OnError(val error: String?) : ScreenChatWithAIEvent()
    data class OnConversationID(val conversationID: String) : ScreenChatWithAIEvent()
    data class OnUserInput(val message: String) : ScreenChatWithAIEvent()
    data class OnSentMessage(val message: Message) : ScreenChatWithAIEvent()
    data class OnBotTyping(val isTyping: Boolean) : ScreenChatWithAIEvent()
    data class OnBotResponse(val message: Message) : ScreenChatWithAIEvent()
    data class OnShowImage(val imageUrl: String) : ScreenChatWithAIEvent()
    data object OnDismissImage : ScreenChatWithAIEvent()
    data class OnInstruction(val markdown: String?) : ScreenChatWithAIEvent()
}

sealed class ScreenChatWithAIEffect : Reducer.ViewEffect {
    data class ShowImageView(val imageUrl: String) : ScreenChatWithAIEffect()
    data object DismissImageView : ScreenChatWithAIEffect()
    data class OnErrorEffect(val message: String?) : ScreenChatWithAIEffect()
}

class ReducerScreenChatAI :
    Reducer<ScreenChatWithAIState, ScreenChatWithAIEvent, ScreenChatWithAIEffect> {
    override fun reduce(
        state: ScreenChatWithAIState,
        event: ScreenChatWithAIEvent
    ): Pair<ScreenChatWithAIState, ScreenChatWithAIEffect?> {
        return when (event) {
            is ScreenChatWithAIEvent.OnLoading -> state.copy(isLoading = event.isLoading) to null
            is ScreenChatWithAIEvent.OnError -> state.copy(error = event.error) to ScreenChatWithAIEffect.OnErrorEffect(event.error)
            is ScreenChatWithAIEvent.OnConversationID -> {
                state.copy(conversationID = event.conversationID) to null
            }

            is ScreenChatWithAIEvent.OnSentMessage -> {
                state.copy(
                    messages = state.messages + event.message
                ) to null
            }

            is ScreenChatWithAIEvent.OnUserInput -> {
                state.copy(
                    inputChat = event.message
                ) to null
            }

            is ScreenChatWithAIEvent.OnBotTyping -> {
                state.copy(
                    isBotTyping = event.isTyping
                ) to null
            }

            is ScreenChatWithAIEvent.OnBotResponse -> {
                state.copy(
                    messages = state.messages + event.message
                ) to null
            }

            is ScreenChatWithAIEvent.OnShowImage -> {
                state to ScreenChatWithAIEffect.ShowImageView(event.imageUrl)
            }

            ScreenChatWithAIEvent.OnDismissImage -> {
                state to ScreenChatWithAIEffect.DismissImageView
            }

            is ScreenChatWithAIEvent.OnInstruction -> {
                state.copy(
                    instruction = event.markdown
                ) to null
            }
        }
    }
}

@HiltViewModel
class ScreenChatWithAIVM @Inject constructor(
    private val chatbotEndpoint: ChatbotEndpoint
) : BaseViewModel<ScreenChatWithAIState, ScreenChatWithAIEvent, ScreenChatWithAIEffect>(
    ScreenChatWithAIState(),
    ReducerScreenChatAI()
) {

    fun getConversationID(chatBotID: String) {
        sendEvent(ScreenChatWithAIEvent.OnLoading(true))
        viewModelScope.launch {
            val result = chatbotEndpoint.getConversationID(mapOf("result_chat_botId" to chatBotID))
            if (result.success == true) {
                sendEvent(ScreenChatWithAIEvent.OnConversationID(result.data?.conversationID ?: ""))
            }
            sendEvent(ScreenChatWithAIEvent.OnLoading(false))
        }
    }


    fun onUserInput(message: String) {
        sendEvent(ScreenChatWithAIEvent.OnUserInput(message))
    }

    fun onUserSentMessage(
        imagePath: String? = null
    ) {
        val message = state.value.inputChat
        if (message != null) {
            val userMessage = Message(
                message = message,
                isFromUser = true,
                timestamp = System.currentTimeMillis()
            )
            sendEvent(
                ScreenChatWithAIEvent.OnSentMessage(userMessage)
            )
            sendMessageToServer(message, imagePath).onEach {
                val botMessageResponse = Message(
                    message = it?.content?.message ?: "",
                    isFromUser = false,
                    timestamp = System.currentTimeMillis()
                )
                val botImageResponse = Message(
                    header = HEADER.IMAGE,
                    message = it?.content?.imageUrl ?: "",
                    isFromUser = false,
                    timestamp = System.currentTimeMillis()
                )
                sendEvent(ScreenChatWithAIEvent.OnBotResponse(botMessageResponse))
                if (!it?.content?.imageUrl.isNullOrEmpty()) {
                    sendEvent(ScreenChatWithAIEvent.OnBotResponse(botImageResponse))
                }
            }.launchIn(viewModelScope)
        }
        onUserInput("")
    }

    // Require passing image for the first init message from the previous screen
    private fun sendMessageToServer(
        message: String,
        imagePath: String? = null
    ) = callbackFlow {
        sendEvent(ScreenChatWithAIEvent.OnBotTyping(true))
        if (imagePath != null) {
            val result = chatbotEndpoint.sendInitMessage(
                MessageRequestTopWrapper(
                    content = InitMessageRequest(
                        message = message,
                        imageURL = imagePath
                    ),
                    conversationId = state.value.conversationID
                )
            )
            if (result.success == true) {
                val botMessage = result.data?.botMessage
                trySend(botMessage)
                sendEvent(ScreenChatWithAIEvent.OnBotTyping(false))
            }
        } else {
            try {
                val result = chatbotEndpoint.sendMessage(
                    MessageRequestTopWrapper(
                        content = MessageRequest(
                            message = message,
                        ),
                        conversationId = state.value.conversationID
                    )
                )
                if (result.success == true) {
                    val botMessage = result.data?.botMessage
                    trySend(botMessage)
                    sendEvent(ScreenChatWithAIEvent.OnBotTyping(false))
                }
            } catch (e: Exception) {
                sendEventWithEffect(ScreenChatWithAIEvent.OnError("Không thể gửi tin nhắn đến AI. Vui lòng thử lại sau."))
                sendEvent(ScreenChatWithAIEvent.OnBotTyping(false))
            }
        }
        awaitClose {
        }
    }


    fun getInstruction() {
        viewModelScope.launch {
            getInstructionServer()
                .catch {
                    sendEvent(ScreenChatWithAIEvent.OnError("Không thể tải hướng dẫn chi tiết. Vui lòng thử lại sau."))
                }
                .onEach {
                sendEvent(ScreenChatWithAIEvent.OnInstruction(it))
            }.launchIn(viewModelScope)
        }
    }

    private fun getInstructionServer() = callbackFlow {
        val result = chatbotEndpoint.getInstruction(mapOf("conversationId" to state.value.conversationID))
        if (result.success == true) {
            trySend(result.data?.markdown)
        }
        awaitClose {

        }
    }

    fun hideErrorMessage() {
        sendEvent(ScreenChatWithAIEvent.OnError(null))
    }

}