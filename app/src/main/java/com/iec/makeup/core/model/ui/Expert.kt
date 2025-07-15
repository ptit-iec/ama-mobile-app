package com.iec.makeup.core.model.ui

import com.google.gson.annotations.SerializedName
import com.iec.makeup.data.remote.dto.Location
import com.iec.makeup.data.remote.dto.MakeUpTemplateDTO
import com.iec.makeup.data.remote.dto.Rating
import kotlinx.serialization.Serializable

@Serializable
data class Expert(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("rating") var rating: Rating? = Rating(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("makeupTemps") var makeupTemps: ArrayList<MakeUpTemplateDTO> = arrayListOf(),
    @SerializedName("certificate") var certificate: ArrayList<String> = arrayListOf(),
    @SerializedName("experienceYears") var experienceYears: Int? = null,
)
