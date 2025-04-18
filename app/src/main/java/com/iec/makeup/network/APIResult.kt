package com.iec.makeup.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class APIResult<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T? = null
)