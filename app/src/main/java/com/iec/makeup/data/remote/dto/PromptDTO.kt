package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class PromptDTO(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("makeupTempId") var makeupTempId: String? = null
)