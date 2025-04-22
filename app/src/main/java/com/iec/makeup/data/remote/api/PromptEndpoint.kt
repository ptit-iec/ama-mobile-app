package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.PromptDTO
import com.iec.makeup.network.APIResult
import retrofit2.http.GET
import retrofit2.http.Query

interface PromptEndpoint {
    @GET("/makeupTempPrompt/all")
    suspend fun getAllPrompt(): APIResult<List<PromptDTO>>


    @GET("/makeupTempPrompt/")
    suspend fun getPromptByID(
        @Query("id") id: String
    ): APIResult<List<PromptDTO>>
}