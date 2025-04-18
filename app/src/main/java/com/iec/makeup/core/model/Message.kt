package com.iec.makeup.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val header: HEADER = HEADER.MESSAGE,
    var message: String,
    val isFromUser: Boolean,
    val timestamp: Long
)

enum class HEADER(val type: String) {
    MESSAGE("message"), FILE("file"), IMAGE("image"), VIDEO("video"), AUDIO("audio")
}
