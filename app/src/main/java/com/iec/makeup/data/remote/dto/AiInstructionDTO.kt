package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class AiInstructionDTO(
    @SerializedName("statusCode") var statusCode: Int? = null,
    @SerializedName("response_type") var responseType: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("markdown") var markdown: String? = null
)