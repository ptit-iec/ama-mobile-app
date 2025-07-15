package com.iec.makeup.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.iec.makeup.ui.features.profiles.booking_history.model.UserBookingUIWrapper
import kotlinx.serialization.Serializable





@Serializable
data class UserBookingDTO(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("expertId") var expertId: String? = null,
    @SerializedName("makeupTempId") var makeupTempId: String? = null,
    @SerializedName("expertMakeupServiceId") var expertMakeupServiceId: String? = null,
    @SerializedName("serviceType") var serviceType: String? = null,
    @SerializedName("bookingTime") var bookingTime: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("makeupPrice") var makeupPrice: Int? = null,
    @SerializedName("travelFee") var travelFee: Int? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("cancelReason") var cancelReason: String? = null,
)

