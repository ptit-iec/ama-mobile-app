package com.iec.makeup.core.utils

import com.iec.makeup.data.remote.dto.Location
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


fun Location.getDistance(currentLat: Double, currentLng: Double): Double {
    val R = 6371
    val lat = this.coordinates[0]
    val lng = this.coordinates[1]
    val dLat = Math.toRadians(currentLat - lat)
    val dLon = Math.toRadians(currentLng - lng)
    val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(currentLat)) * cos(Math.toRadians(lat)) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}