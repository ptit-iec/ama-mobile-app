package com.iec.makeup.data.repository

import com.iec.makeup.data.remote.dto.MakeUpTemplateCategoryDTO


interface MakeUpTemplateCategoryRepository {

    suspend fun getAllMakeUpTemplateCategory(): List<MakeUpTemplateCategoryDTO>

    suspend fun getMakeUpTemplateCategoryById(id: String): MakeUpTemplateCategoryDTO?
}