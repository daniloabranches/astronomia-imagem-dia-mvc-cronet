package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.cronet.CronetEngineAdapter
import com.exemplo.astroimagemdodia.data.cronet.CallerImp
import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity

class NasaServiceImp(
    private val cronetEngineAdapter: CronetEngineAdapter
) : NasaService {
    override fun getImageDay(): CallerImp<ImageDayDataEntity, ImageDayEntity> {
        return cronetEngineAdapter.createCaller(CronetEngineAdapter.Method.GET, "planetary/apod")
    }
}