package com.iec.makeup.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray


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


@Serializable
data class APIResultPaging<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null

)


@Serializable
data class APIUploadResult(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("files")
    val files: List<UploadResponse>
)

@Serializable
data class UploadResponse(
    @SerializedName("fieldname") var fieldname: String? = null,
    @SerializedName("originalname") var originalname: String? = null,
    @SerializedName("mimetype") var mimetype: String? = null,
    @SerializedName("filename") var filename: String? = null,
    @SerializedName("filepath") var filepath: String? = null,
    @SerializedName("filesize") var filesize: String? = null,
    @SerializedName("url") var url: String? = null
)