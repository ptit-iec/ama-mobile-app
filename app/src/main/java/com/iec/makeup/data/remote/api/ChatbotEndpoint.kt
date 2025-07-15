package com.iec.makeup.data.remote.api

import com.google.gson.annotations.SerializedName
import com.iec.makeup.data.remote.dto.AIResponses
import com.iec.makeup.data.remote.dto.AiInstructionDTO
import com.iec.makeup.data.remote.dto.BotResponse
import com.iec.makeup.data.remote.dto.ConversationIDResponse
import com.iec.makeup.data.remote.dto.WrapperResponse
import com.iec.makeup.network.APIResult
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.Serial


@Serializable
data class ChatbotRequest(
    val prompt: String,
    val makeupTempId: String,
    @SerializedName("image_request") val imageRequest: String
)

@Serializable
data class InitMessageRequest(
    val message: String,
    @SerializedName("image_url") val imageURL: String,
)

@Serializable
data class MessageRequest(
    val message: String,
)

data class MessageRequestTopWrapper<T>(
    @SerializedName("content") var content: T?,
    @SerializedName("conversationId") var conversationId: String? = null
)

interface ChatbotEndpoint {
    @POST("questionSession")
    suspend fun getInitAnswer(@Body body: ChatbotRequest): APIResult<WrapperResponse>

    @POST("conversation")
    suspend fun getConversationID(@Body body: Map<String, String>): APIResult<ConversationIDResponse>

    @POST("message")
    suspend fun sendMessage(@Body body: MessageRequestTopWrapper<MessageRequest>): APIResult<BotResponse>

    @POST("message")
    suspend fun sendInitMessage(@Body body: MessageRequestTopWrapper<InitMessageRequest>): APIResult<BotResponse>

    @POST("makeup/introduction")
    suspend fun getInstruction(
        @Body body: Map<String, String>
    ) : APIResult<AiInstructionDTO>
}