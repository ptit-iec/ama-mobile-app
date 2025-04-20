package com.iec.makeup.data.remote.repository

import com.iec.makeup.data.remote.api.MakeUpTempCategoryEndpoint
import com.iec.makeup.data.remote.dto.MakeUpTemplateCategoryDTO
import com.iec.makeup.data.repository.MakeUpTemplateCategoryRepository
import javax.inject.Inject


class MakeUpTemplateCategoryImpl @Inject constructor(
    private val makeUpTempCategoryEndpoint: MakeUpTempCategoryEndpoint
) : MakeUpTemplateCategoryRepository{
    override suspend fun getAllMakeUpTemplateCategory(): List<MakeUpTemplateCategoryDTO> {
        val result = makeUpTempCategoryEndpoint.getAllMakeUpTemplateCategory()
        return result.data ?: emptyList()
    }

    override suspend fun getMakeUpTemplateCategoryById(id: String): MakeUpTemplateCategoryDTO? {
        val result = makeUpTempCategoryEndpoint.getMakeUpTemplateCategoryById(id)
        if(result.isSuccess){
            val data = result.getOrNull()
            return data?.data
        }else{
            return null
        }
    }
}