package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class UserReviewExpertDTO(
    @SerializedName("id") var id: String? = null,
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("userAvatar") var userAvatar: String? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("makeupType") var makeupType: String? = null
)