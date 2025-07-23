package com.iec.makeup.data.remote.api.ver2

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Named


@Serializable
data class ChatRequest(
    val message: String,
    @SerializedName("session_id") val sessionId: String,
    @SerializedName("agent_type") val agentType: String = "makeup",
    @SerializedName("show_thinking") val showThinking: Boolean = true
)

@Serializable
data class ChatResponse (

    @SerializedName("success"    ) var success   : Boolean? = null,
    @SerializedName("session_id" ) var sessionId : String?  = null,
    @SerializedName("message"    ) var message   : String?  = null,
    @SerializedName("stream_url" ) var streamUrl : String?  = null

)

interface TalkAIEndpoint {
    @POST("/api/v1/chat/chat")
    suspend fun sendNormalMessage(
        @Body chatRequest: ChatRequest
    ): ChatResponse

    @POST("/api/v1/chat/upload-and-chat")
    suspend fun sendImageAndChatMessage(): String
}