package com.iec.makeup.data.repository

import com.iec.makeup.data.remote.dto.UserBookingDTO

interface BookingRepository {
    suspend fun getUserBooking() : List<UserBookingDTO>?
}