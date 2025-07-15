package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SamplesByCategory(

    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("samples") var samples: ArrayList<Samples> = arrayListOf()

)

@Serializable
data class Samples(

    @SerializedName("id") var id: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("makeupTitle") var makeupTitle: String? = null

)


@Serializable
data class ExpertDetail(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("certificate") var certificate: ArrayList<String> = arrayListOf(),
    @SerializedName("experienceYears") var experienceYears: Int? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("samplesByCategory") var samplesByCategory: ArrayList<SamplesByCategory> = arrayListOf()
)