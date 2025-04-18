package com.iec.makeup.data.repository

import com.iec.makeup.data.remote.api.AuthEndpoint
import com.iec.makeup.data.remote.api.LoginRequest
import com.iec.makeup.data.remote.dto.LoginDTO
import com.iec.makeup.network.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

interface AuthRepository {
    suspend fun doLogin(username: String, password: String): Response<APIResult<LoginDTO?>>
    suspend fun doRegistration(username: String, password: String)
}

class AuthRepositoryImpl @Inject constructor(
    private val authRemote: AuthEndpoint
) : AuthRepository {
    override suspend fun doLogin(
        username: String,
        password: String
    ): Response<APIResult<LoginDTO?>> {
        return withContext(Dispatchers.IO) {
            authRemote.login(
                LoginRequest(
                    email = username,
                    password = password
                )
            )
        }
    }

    override suspend fun doRegistration(username: String, password: String) {

    }

}