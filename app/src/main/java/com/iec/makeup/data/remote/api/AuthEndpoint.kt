package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.LoginDTO
import com.iec.makeup.network.APIResult
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)


interface AuthEndpoint {
    @POST("authLocal/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<APIResult<LoginDTO?>>

}