package com.iec.makeup.data.remote.repository

import com.iec.makeup.core.model.Message
import com.iec.makeup.core.network.SocketIOManager
import com.iec.makeup.core.network.WebSocketClient
import com.iec.makeup.data.remote.dto.MessageDTO
import com.iec.makeup.data.repository.MessageChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


class MessageChatRemoteImpl @Inject constructor(
    private val socketIOManager: SocketIOManager,

    ) : MessageChatRepository {


    override suspend fun sendMessage(message: MessageDTO) {
        socketIOManager.sendMessage(message)
    }

    override suspend fun receiveMessage(): Flow<MessageDTO> {
        return flow {
            socketIOManager.incomingMessages.collect { message ->
                emit(
                    MessageDTO(
                        text = message,
                        sender = "user",
                        timestamp = "${System.currentTimeMillis()}"
                    )
                )
            }
        }
    }
}