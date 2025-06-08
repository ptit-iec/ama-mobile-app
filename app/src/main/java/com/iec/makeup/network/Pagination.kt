package com.iec.makeup.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Pagination(
    @SerializedName("totalItems") var totalItems: Int? = null,
    @SerializedName("itemsPerPage") var itemsPerPage: Int? = null,
    @SerializedName("currentPage") var currentPage: Int? = null,
    @SerializedName("totalPages") var totalPages: Int? = null,
    @SerializedName("hasNextPage") var hasNextPage: Boolean? = null,
    @SerializedName("hasPrevPage") var hasPrevPage: Boolean? = null
){
    val nextPage: Int?
        get() = if (hasNextPage == true) currentPage?.plus(1) else null
    val prevPage: Int?
        get() = if (hasPrevPage == true) currentPage?.minus(1) else null




}