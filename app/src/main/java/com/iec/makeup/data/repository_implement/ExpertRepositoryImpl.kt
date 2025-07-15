package com.iec.makeup.data.repository_implement

import android.util.Log
import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.remote.api.ExpertEndpoint
import com.iec.makeup.data.remote.dto.ExpertDetail
import com.iec.makeup.data.remote.dto.UserReviewExpertDTO
import com.iec.makeup.data.remote.dto.toExpert
import com.iec.makeup.data.repository.ExpertRepository
import com.iec.makeup.network.Pagination
import javax.inject.Inject

class ExpertRepositoryImpl @Inject constructor(
    private val expertEndpoint: ExpertEndpoint
) : ExpertRepository {


    private var allExpertPagination: Pagination? = null

    override suspend fun getAllExperts(
        isFirsTimeCall: Boolean
    ): List<Expert> {
        if (isFirsTimeCall) {
            allExpertPagination = null
        }
        if(allExpertPagination?.hasNextPage == false) return emptyList()
        try {
            val result = expertEndpoint.getAllExperts(
                page = allExpertPagination?.nextPage ?: 1,
                limit = allExpertPagination?.itemsPerPage ?: 10
            )
            Log.d("ExpertRepositoryImpl", "getAllExperts: $result")
            if (result.success == true) {
                allExpertPagination = result.pagination
                return result.data?.map { it.toExpert() } ?: emptyList()
            } else {
                throw Exception(result.message)
            }
        } catch (
            e: Exception
        ) {
            Log.d("ExpertRepositoryImpl", "getAllExperts: ${e.toString()}")
        }
        return emptyList()
    }

    override suspend fun getExpertByID(id: String): ExpertDetail {
        try {
            val result = expertEndpoint.getExpertDetail(id)
            if (result.success == true) {
                return result.data ?: throw Exception("No data")
            } else {
                throw Exception(result.message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserReviewsExpert(expertID: String): List<UserReviewExpertDTO> {
        try {
            val result = expertEndpoint.getExpertRate(expertID)
            if (result.success == true) {
                return result.data ?: throw Exception("No data")
            } else {
                throw Exception(result.message)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}