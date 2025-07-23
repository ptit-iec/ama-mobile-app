package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class DataEventThinking(
    val step: String,
    val content: String,
    val timestamp: String
)

@Serializable
data class DataEventAgentResponse(
    val response: String,
    val agent_type: String,
    val timestamp: String
)

@Serializable
data class DataEventStatus (

    @SerializedName("status"    ) var status    : String?  = null,
    @SerializedName("details"   ) var details   : Details? = Details(),
    @SerializedName("timestamp" ) var timestamp : String?  = null

)

@Serializable
data class Details (
    @SerializedName("task_id"   ) var taskId   : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("progress" ) var progress : Float? = null
)




sealed class EventDataClass {
    @Serializable
    data class EventThinking(
        val event: String,
        val data: DataEventThinking,
    )

    @Serializable
    data class EventAgentResponse(
        val event: String,
        val data: DataEventAgentResponse,
    )

    @Serializable
    data class EventStatus(
        val event: String,
        val data: DataEventStatus
    )
}