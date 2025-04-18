package com.iec.makeup.core.model

import com.iec.makeup.data.remote.dto.UserDTO
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: String? = "Default",
    val name: String? = "Default",
    val email: String? = "Default",
    val phone: String? = "Default",
    val avatar: String? = "Default",
)


fun User.fromUserDTO(userDTO: UserDTO): User {
    return User(
        id = userDTO.id,
        name = userDTO.name,
        email = userDTO.email,
        phone = userDTO.phone,
        avatar = userDTO.avatar
    )
}