package com.iec.makeup.ui.features.profiles.booking_history.model

enum class HistoryCategory(
    val title: String,
    val titleVN: String,
) {
    PENDING("pending", "Đang chờ"),
    CONFIRMED("confirmed", "Xác nhận"),
    COMPLETED("completed", "Hoàn thành"),
    CANCELED("canceled", "Đã hủy")
}