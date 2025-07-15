package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.PromptDTO
import com.iec.makeup.network.APIResult
import com.iec.makeup.network.APIUploadResult
import kotlinx.serialization.json.JsonObject
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PromptEndpoint {
    @GET("/makeupTempPrompt/all")
    suspend fun getAllPrompt(): APIResult<List<PromptDTO>>


    @GET("/makeupTempPrompt/")
    suspend fun getPromptByID(
        @Query("id") id: String
    ): APIResult<List<PromptDTO>>


    @Multipart
    @POST("/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ) : APIUploadResult
}