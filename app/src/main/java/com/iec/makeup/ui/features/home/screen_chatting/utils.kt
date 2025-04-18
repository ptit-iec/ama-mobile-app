package com.example.iec.ui.feature.main.message

fun convertTimeStamp(timeInMilli: Long): String {
    return when (val timeInSecond = timeInMilli / 1000) {
        in 0..59 -> "Send just now"
        in 60..3599 -> "${timeInSecond / 60} minutes ago"
        in 3600..86400 -> "${timeInSecond / 3600} hours ago"
        else -> {
            "Send farther before. "
        }
    }
}


