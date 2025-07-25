package com.iec.makeup.data.remote.api.ver2

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
data class UploadChatResponse (
    @SerializedName("success"       ) var success      : Boolean?      = null,
    @SerializedName("session_id"    ) var sessionId    : String?       = null,
    @SerializedName("message"       ) var message      : String?       = null,
    @SerializedName("stream_url"    ) var streamUrl    : String?       = null,
    @SerializedName("uploaded_file" ) var uploadedFile : UploadedFile? = UploadedFile()
)
data class UploadedFile (
    @SerializedName("filename"      ) var filename     : String? = null,
    @SerializedName("original_name" ) var originalName : String? = null,
    @SerializedName("file_path"     ) var filePath     : String? = null
)

interface TalkAIEndpoint {
    @POST("/api/v1/chat/chat")
    suspend fun sendNormalMessage(
        @Body chatRequest: ChatRequest
    ): ChatResponse

    @Multipart
    @POST("/api/v1/chat/upload-and-chat")
    suspend fun sendImageAndChatMessage(
        @Part file: MultipartBody.Part,
        @Part("message") message: RequestBody,
        @Part("session_id") sessionId: RequestBody,
        @Part("show_thinking") showThinking: RequestBody
    ): UploadChatResponse
}