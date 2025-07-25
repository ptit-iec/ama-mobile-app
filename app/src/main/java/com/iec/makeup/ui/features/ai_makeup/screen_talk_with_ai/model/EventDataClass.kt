package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model

import kotlinx.serialization.SerialName
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
data class DataEventStatus(

    @SerialName("status") var status: String? = null,
    @SerialName("details") var details: Details? = Details(),
    @SerialName("timestamp") var timestamp: String? = null

)

@Serializable
data class Details(
    @SerialName("task_id") var taskId: String? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("progress") var progress: Float? = null,
    @SerialName("task_type") var taskType: String? = null,
    @SerialName("result") var result: Result? = null

)

@Serializable
data class MakeupResult(
    @SerialName("image_url") var imageUrl: String? = null,
    @SerialName("style_name") var styleName: String? = null,
    @SerialName("original_path") var originalPath: String? = null,
    @SerialName("timestamp") var timestamp: String? = null

)

@Serializable
data class Result(
    @SerialName("success") var success: Boolean? = null,
    @SerialName("session_id") var sessionId: String? = null,
    @SerialName("facial_part") var facialPart: String? = null,
    @SerialName("style_name") var styleName: String? = null,
    @SerialName("prompt_used") var promptUsed: String? = null,
    @SerialName("negative_prompt_used") var negativePromptUsed: String? = null,
    @SerialName("seed") var seed: Int? = null,
    @SerialName("intensity") var intensity: Double? = null,
    @SerialName("adjusted_intensity") var adjustedIntensity: Double? = null,
    @SerialName("preserve_structure") var preserveStructure: Boolean? = null,
    @SerialName("mask_coverage") var maskCoverage: Double? = null,
    @SerialName("result_path") var resultPath: String? = null,
    @SerialName("result_filename") var resultFilename: String? = null,
    @SerialName("image_size") var imageSize: ArrayList<Int> = arrayListOf(),
    @SerialName("scale_factor") var scaleFactor: Int? = null,
    @SerialName("makeup_description") var makeupDescription: String? = null
)


sealed class EventDataClass {
    @Serializable
    data class EventThinking(
        val event: String,
        val data: DataEventThinking,
    ) : EventDataClass()

    @Serializable
    data class EventAgentResponse(
        val event: String,
        val data: DataEventAgentResponse,
    ) : EventDataClass()

    @Serializable
    data class EventStatus(
        val event: String,
        val data: DataEventStatus
    ) : EventDataClass()

    @Serializable
    data class EventResult(
        val event: String,
        val data: MakeupResult
    ) : EventDataClass()

}