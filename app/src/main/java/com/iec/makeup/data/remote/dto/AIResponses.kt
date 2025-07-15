package com.iec.makeup.data.remote.dto

import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName



@Serializable
data class WrapperResponse(
    @SerializedName("aiResponse") var aiResponse: AIResponses? = null
)


@Serializable
data class AIResponses(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("imageUrlFirst") var imageUrlFirst: String? = null,
    @SerializedName("result_chat_botId") var resultChatBotID: String? = null,
    @SerializedName("makeup_images") var makeupImages: List<MakeupImages> = listOf(),
    @SerializedName("face_analysis") var faceAnalysis: FaceAnalysis? = FaceAnalysis()
)


@Serializable
data class MakeupImages(

    @SerializedName("type") var type: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null

)


@Serializable
data class FaceAnalysis(

    @SerializedName("face_shape") var faceShape: String? = null,
    @SerializedName("skin_tone") var skinTone: String? = null

)


@Serializable
data class ConversationIDResponse(
    @SerializedName("_id") var conversationID: String? = null
)


/*
   BOT RESPONSE
 */
@Serializable
data class BotResponse (

    @SerializedName("botMessage" ) var botMessage : BotMessage? = BotMessage()

)
@Serializable
data class Content (

    @SerializedName("message"   ) var message  : String? = null,
    @SerializedName("image_url" ) var imageUrl : String? = null

)
@Serializable
data class BotMessage (

    @SerializedName("isBot"          ) var isBot          : Boolean? = null,
    @SerializedName("content"        ) var content        : Content? = Content(),
    @SerializedName("conversationId" ) var conversationId : String?  = null,
    @SerializedName("_id"            ) var Id             : String?  = null,
    @SerializedName("created_at"     ) var createdAt      : String?  = null,
    @SerializedName("updated_at"     ) var updatedAt      : String?  = null

)