package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.iec.makeup.core.model.ui.Expert
import kotlinx.serialization.Serializable


@Serializable
data class Rating(

    @SerializedName("average") var average: Double? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("display") var display: String? = null

)


@Serializable
data class Location(

    @SerializedName("type") var type: String? = null,
    @SerializedName("coordinates") var coordinates: ArrayList<Double> = arrayListOf(),
    @SerializedName("address") var address: String? = null

)


@Serializable
data class ExpertDTO(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("rating") var rating: Rating? = Rating(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("makeupTemps") var makeupTemps: ArrayList<MakeUpTemplateDTO> = arrayListOf(),
//    @SerializedName("certificate") var certificate: ArrayList<String> = arrayListOf(),
    @SerializedName("experienceYears") var experienceYears: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)




fun ExpertDTO.toExpert(): Expert {
    return Expert(
        Id = Id ?: "",
        name = name ?: "",
        avatar = avatar ?: "",
        description = description ?: "",
        rating = rating ?: Rating(),
        location = location ?: Location(),
        phone = phone ?: "",
        makeupTemps = makeupTemps,
//        certificate = certificate,
        experienceYears = experienceYears ?: 0
    )
}