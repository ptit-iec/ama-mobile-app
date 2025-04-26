package com.iec.makeup.data.remote.api

import com.google.gson.annotations.SerializedName
import com.iec.makeup.data.remote.dto.ExpertDTO
import com.iec.makeup.data.remote.dto.ExpertDetail
import com.iec.makeup.network.APIResult
import com.iec.makeup.network.APIResultPaging
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serial

@Serializable
data class DataListExpert(
    @SerializedName("list_expert") val data: List<ExpertDTO>
)

interface ExpertEndpoint {
    @GET("list_expert/all")
    suspend fun getAllExperts(@Query("page") page: Int, @Query("limit") limit: Int): APIResultPaging<List<ExpertDTO>>

    @GET("/expertDetail/{id}")
    suspend fun getExpertDetail(@Path("id") id: String): APIResult<ExpertDetail>

}