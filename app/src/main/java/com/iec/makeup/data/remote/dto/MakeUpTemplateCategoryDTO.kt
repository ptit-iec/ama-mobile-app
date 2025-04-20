package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.iec.makeup.core.model.ui.MakeUpLayout
import kotlinx.serialization.Serializable


@Serializable
data class MakeUpTemplateCategoryDTO(
    @SerializedName("_id") var id: String? = null,
    @SerializedName("makeupTempId") var makeupTempId: List<String>? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("images") var images: List<String> = listOf(),
)

fun MakeUpTemplateCategoryDTO.toMakeUpTemplateCategory(): MakeUpLayout {
    return MakeUpLayout(
        id = id ?: "",
        title = title ?: "",
        image = if(images.isNotEmpty()) images[0] else "",
        makeUpTemplateId = makeupTempId ?: emptyList(),
        description = description ?: ""
    )
}