package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.UserBookingDTO
import com.iec.makeup.network.APIResult
import retrofit2.http.GET


interface ProfileEndpoint {
    @GET("booking")
    suspend fun getAllUserBooked(): APIResult<List<UserBookingDTO>>
}