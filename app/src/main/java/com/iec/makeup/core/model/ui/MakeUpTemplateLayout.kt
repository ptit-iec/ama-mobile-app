package com.iec.makeup.core.model.ui

import kotlinx.serialization.Serializable


@Serializable
data class MakeUpTemplateLayout(
    val id: String? = null,
    val makeupTempCategoryId: ArrayList<String> = arrayListOf(),
    val title: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,

)

val mockMakeUpTemplateLayout = listOf(
    MakeUpTemplateLayout(
        id = "1",
        makeupTempCategoryId = arrayListOf("1"),
        title = "title",
        description = "description",
        thumbnail = "thumbnail",
    ),
    MakeUpTemplateLayout(
        id = "2",
        makeupTempCategoryId = arrayListOf("1"),
        title = "title",
        description = "description",
        thumbnail = "thumbnail",
    ),
    MakeUpTemplateLayout(
        id = "3",
        makeupTempCategoryId = arrayListOf("1"),
        title = "title",
        description = "description",
        thumbnail = "thumbnail",
    ),
)