package com.iec.makeup.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class MessageDTO(
    val text: String,
    val sender: String,
    val timestamp: String
) {
}
