package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout


data class MakeUpTemplateDTO(

    @SerializedName("makeupTempCategoryId") var makeupTempCategoryId: ArrayList<String> = arrayListOf(),
    @SerializedName("_id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null

)

fun MakeUpTemplateDTO.toMakeUpTemplate(): MakeUpTemplateLayout {
    return MakeUpTemplateLayout(
        id = id ?: "",
        makeupTempCategoryId = makeupTempCategoryId,
        title = title ?: "",
        description = description ?: "",
        thumbnail = thumbnail ?: ""
    )
}