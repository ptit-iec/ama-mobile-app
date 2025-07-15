package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.LoginDTO
import com.iec.makeup.network.APIResult
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class GoogleLoginRequest(
    val code: String,
)

interface AuthEndpoint {
    @POST("authLocal/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<APIResult<LoginDTO?>>

    @POST("auth/login-success")
    suspend fun loginWithGoogle(
        @Body body: GoogleLoginRequest
    ): Response<APIResult<LoginDTO?>>

}