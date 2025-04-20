package com.iec.makeup.data.remote.repository

import com.iec.makeup.data.remote.api.MakeUpTemplateEndpoint
import com.iec.makeup.data.remote.dto.MakeUpTemplateDTO
import com.iec.makeup.data.repository.MakeUpTemplateRepository
import javax.inject.Inject


class MakeUpTemplateRepositoryImpl @Inject constructor(
    private val makeUpTemplateEndpoint: MakeUpTemplateEndpoint
) : MakeUpTemplateRepository {
    override suspend fun getAllMakeUpTemplate(): List<MakeUpTemplateDTO> {
        return emptyList()
    }

    override suspend fun getMakeUpTemplateById(id: String): MakeUpTemplateDTO? {
        val result = makeUpTemplateEndpoint.getMakeUpTemplateById(id)
        if(result.success == true){
            return result.data
        }else{
            return null
        }
    }

}