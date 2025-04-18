package com.iec.makeup.core.model.ui

import kotlinx.serialization.Serializable


@Serializable
data class MakeUpTemplateLayout(
    val id: String,
    val image: String,
    val prompt: String,
    val isFavorite: Boolean = false,
)

val mockMakeUpTemplateLayout = listOf(
    MakeUpTemplateLayout(
        "1",
        "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
        "Prompt 1",
        false
    ),
    MakeUpTemplateLayout(
        "2",
        "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
        "Prompt 2",
        true
    ),
    MakeUpTemplateLayout(
        "3",
        "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
        "Prompt 3",
        false
    ),
    MakeUpTemplateLayout(
        "4",
        "https://i.pinimg.com/736x/86/2f/31/862f310c3e879aefcbf50748758e32cc.jpg",
        "Prompt 4",
        true
    ),

    )