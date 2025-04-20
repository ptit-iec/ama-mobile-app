package com.iec.makeup.data.remote.api

import com.iec.makeup.data.remote.dto.MakeUpTemplateCategoryDTO
import com.iec.makeup.network.APIResult
import retrofit2.http.GET

interface MakeUpTempCategoryEndpoint {
    @GET("makeupTempCategory/all")
    suspend fun getAllMakeUpTemplateCategory():APIResult<List<MakeUpTemplateCategoryDTO>>

    @GET("makeupTempCategory")
    suspend fun getMakeUpTemplateCategoryById(id: String): Result<APIResult<MakeUpTemplateCategoryDTO>>
}