package com.iec.makeup.data.repository

import com.iec.makeup.data.remote.dto.MakeUpTemplateDTO

interface MakeUpTemplateRepository {
    suspend fun getAllMakeUpTemplate(): List<MakeUpTemplateDTO>
    suspend fun getMakeUpTemplateById(id: String): MakeUpTemplateDTO?
}