package com.iec.makeup.core.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class MakeUpLayout(
    val id: String,
    val title: String,
    val image: String
)


fun fakeMakeUpLayoutData(): List<MakeUpLayout> {
    return listOf(
        MakeUpLayout(
            id = "1",
            title = "Portrait",
            image = "https://cdn.motiondesign.school/uploads/2021/06/GIF_Header.gif"
        ),
        MakeUpLayout(
            id = "2",
            title = "Landscape",
            image = "https://cdn.motiondesign.school/uploads/2021/06/GIF_Header.gif"
        ),
        MakeUpLayout(
            id = "3",
            title = "Wedding",
            image = "https://lfactorcosmetics.com/cdn/shop/articles/Essential_makeup_products.jpg?v=1701681592"
        ),
        MakeUpLayout(
            id = "4",
            title = "Wedding",
            image = "https://lfactorcosmetics.com/cdn/shop/articles/Essential_makeup_products.jpg?v=1701681592"
        ),
    )
}