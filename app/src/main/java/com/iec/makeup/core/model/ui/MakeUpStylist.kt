package com.iec.makeup.core.model.ui

import kotlinx.serialization.Serializable


@Serializable
data class MakeUpStylist(
    val id: String,
    val name: String,
    val imageUrl: String,
    // Pay one time or pay hourly
    val priceType: String,
    val price: Double,
    val detail: String,
    val monthlyOrder: Int,
    val rating: Double,
    val ratingCount: Int,
    val location: String,
    val isFavorite: Boolean,
    val isVerified: Boolean,
    val isOnline: Boolean,
    val isAvailable: Boolean,
)


val mockListData = mutableListOf<MakeUpStylist>().apply {
    repeat(
        10
    ) {
        add(
            MakeUpStylist(
                id = it.toString(),
                name = "MakeUp Stylist $it",
                imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                priceType = "Hourly",
                price = 20.0,
                detail = "Makeup artist with a wide range of experience in various styles.",
                rating = 4.5,
                ratingCount = 100,
                location = "New York",
                isFavorite = false,
                isVerified = true,
                isOnline = true,
                isAvailable = true,
                monthlyOrder = 124,
            )
        )
    }
}
