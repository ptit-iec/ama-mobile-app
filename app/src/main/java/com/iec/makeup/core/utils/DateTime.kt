package com.iec.makeup.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object DateTimeUtils {
    private val fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")


    fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now()

    fun getCurrentDate(): String = getCurrentDateTime().format(dateFormatter)

    fun getCurrentTime(): String = getCurrentDateTime().format(hourFormatter)

    fun getCurrentFullDateTime(): String = getCurrentDateTime().format(fullDateTimeFormatter)

    fun LocalDateTime.isMorning(): Boolean = this.hour < 12
}