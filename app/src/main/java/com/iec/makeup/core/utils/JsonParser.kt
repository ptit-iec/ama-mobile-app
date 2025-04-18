package com.iec.makeup.core.utils

import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> String.fromJson(): T = json.decodeFromString<T>(this)

// For encode, just Json.encodeToString(object)