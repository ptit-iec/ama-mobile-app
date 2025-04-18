package com.iec.makeup.data.repository

import com.iec.makeup.data.remote.dto.MessageDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface MessageChatRepository {
    suspend fun sendMessage(message: MessageDTO): Unit
    suspend fun receiveMessage(): Flow<MessageDTO>
}


