package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.UserDTO
import com.iec.makeup.network.APIResult
import retrofit2.Response
import retrofit2.http.GET


interface UserEndpoint {
    @GET("auth/me")
    suspend fun getUsers(): Response<APIResult<UserDTO>>
}