package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.iec.makeup.core.model.User
import kotlinx.serialization.Serializable


@Serializable
data class UserDTO(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("googleId") var googleId: String? = null,
    @SerializedName("isGoogle") var isGoogle: Boolean? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("address") var address: String? = null,
)

fun UserDTO.toUser(): User = User(id, name, email, phone, avatar)