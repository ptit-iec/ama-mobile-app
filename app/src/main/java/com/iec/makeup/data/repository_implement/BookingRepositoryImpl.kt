package com.iec.makeup.data.repository_implement

import com.iec.makeup.data.remote.api.ProfileEndpoint
import com.iec.makeup.data.remote.dto.UserBookingDTO
import com.iec.makeup.data.repository.BookingRepository
import javax.inject.Inject


class BookingRepositoryImpl @Inject constructor(
    private val profileEndpoint: ProfileEndpoint
) : BookingRepository {
    override suspend fun getUserBooking(): List<UserBookingDTO>? {
        val result = profileEndpoint.getAllUserBooked()
        if(result.success == true ){
            return result.data ?: emptyList()

        }
        return null
    }
}