package com.iec.makeup.data.repository

import com.iec.makeup.core.model.ui.Expert
import com.iec.makeup.data.remote.dto.ExpertDTO
import com.iec.makeup.data.remote.dto.ExpertDetail

interface ExpertRepository {
    suspend fun getAllExperts(
        isFirsTimeCall: Boolean = true
    ) : List<Expert>
    suspend fun getExpertByID(id: String) : ExpertDetail?
}