package com.iec.makeup.core.model

import kotlinx.serialization.Serializable


@Serializable
data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean = false
)

val mockNotificationList = mutableListOf<Notification>().apply {
    repeat(10) {
        add(
            Notification(
                id = it,
                title = "Notification $it",
                message = "This is notification $it",
                timestamp = System.currentTimeMillis(),
                isRead = false
            )
        )
    }
}