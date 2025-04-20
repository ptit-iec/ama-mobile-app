package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.MakeUpTemplateDTO
import com.iec.makeup.network.APIResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeUpTemplateEndpoint {
    @GET("makeupTemp/all")
    suspend fun getAllMakeUpTemplate(): APIResult<List<MakeUpTemplateDTO>>

    @GET("makeupTemp/")
    suspend fun getMakeUpTemplateById(
        @Query("id") id: String): APIResult<MakeUpTemplateDTO>

}