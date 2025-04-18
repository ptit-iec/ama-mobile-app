package com.iec.makeup.ui.features.home.screen_chatting.box_chat_message

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.model.Message
import com.iec.makeup.core.network.SocketIOManager
import com.iec.makeup.core.network.SocketStateListener
import com.iec.makeup.core.network.WebSocketClient
import com.iec.makeup.data.remote.dto.MessageDTO
import com.iec.makeup.data.repository.MessageChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject


data class MessageScreenState(
    val chats: SnapshotStateList<Message> = mutableStateListOf(),
    val userInput: String? = null,
    val targetIsTyping: Boolean = false,
) : Reducer.ViewState


sealed class MessageScreenEvent : Reducer.ViewEvent {
    data class InputMessageChange(val message: String) : MessageScreenEvent()
    data class SendMessage(val message: Message) : MessageScreenEvent()
    data object RefreshMessage : MessageScreenEvent()
    data class Error(val error: String) : MessageScreenEvent()
}

sealed class MessageScreenEffect : Reducer.ViewEffect {
    data class ShowToast(val message: String) : MessageScreenEffect()
    data class ShowError(val error: String) : MessageScreenEffect()
}

class MessageScreenReducer : Reducer<MessageScreenState, MessageScreenEvent, MessageScreenEffect> {
    override fun reduce(
        currentState: MessageScreenState,
        event: MessageScreenEvent
    ): Pair<MessageScreenState, MessageScreenEffect?> {
        return when (event) {
            is MessageScreenEvent.InputMessageChange -> {
                return currentState.copy(
                    userInput = event.message
                ) to null
            }

            is MessageScreenEvent.SendMessage -> {
                val newChats = currentState.chats.apply {
                    add(event.message)
                }
                return currentState.copy(
                    chats = newChats
                ) to null
            }

            is MessageScreenEvent.RefreshMessage -> {
                return currentState to null
            }

            else -> {
                currentState to null
            }
        }
    }
}

@HiltViewModel
class ChatMessageVM @Inject constructor(
    private val messageChatRepository: MessageChatRepository,
    private val socketIOManager: SocketIOManager
) : BaseViewModel<MessageScreenState, MessageScreenEvent, MessageScreenEffect>(
    initialState = MessageScreenState(),
    reducer = MessageScreenReducer()
) {

    init {
        socketIOManager.setListener(object : SocketStateListener {
            override fun onSocketConnected() {
                Log.d("ChatMessageVM", "Socket connected")
            }

            override fun onSocketDisconnected() {
                Log.d("ChatMessageVM", "Socket disconnected")
            }

        })
        socketIOManager.initSocket("chat", listOf("message"))
        onReceiveMessage()
    }

    private val scope = viewModelScope + CoroutineExceptionHandler { _, error ->
        Log.d("ChatMessageVM", "Error: ${error.message}")
    }

    fun onMessageChange(message: String) {
        sendEvent(MessageScreenEvent.InputMessageChange(message))
    }

    fun onSendMessage() {
        val message = state.value.userInput
        if (message != null) {
            val userMessage = Message(
                message = state.value.userInput!!,
                isFromUser = true,
                timestamp = System.currentTimeMillis()
            )
            sendEvent(
                MessageScreenEvent.SendMessage(
                    userMessage
                )
            )
            scope.launch {
                messageChatRepository.sendMessage(
                    MessageDTO(
                        text = message,
                        sender = "user",
                        timestamp = "${Date().time}"
                    )
                )
            }
        }
    }

    private fun onReceiveMessage() {
        viewModelScope.launch {
            try {
                messageChatRepository.receiveMessage()
                    .flowOn(Dispatchers.IO)
                    .catch { error ->
                        sendEventWithEffect(
                            MessageScreenEvent.Error(
                                error.message ?: "Unknown error"
                            )
                        )
                        Log.e("ChatMessageVM", "Error receiving messages", error)
                    }
                    .collect { receivedMessage ->
                        val userMessage = Message(
                            message = receivedMessage.text,
                            isFromUser = false,
                            timestamp = System.currentTimeMillis()
                        )
                        withContext(Dispatchers.Main) {
                            sendEvent(MessageScreenEvent.SendMessage(userMessage))
                        }
                    }
            } catch (e: Exception) {
                Log.e("ChatMessageVM", "Error receiving messages", e)
            }
        }
    }
}