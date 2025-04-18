package com.iec.makeup.core.utils


fun String.validatesEmailPattern(): Boolean {
    val emailRegex = Regex(pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(this)
}