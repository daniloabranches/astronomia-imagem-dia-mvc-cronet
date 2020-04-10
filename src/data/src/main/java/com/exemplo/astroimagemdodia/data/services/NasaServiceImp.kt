package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.cronet.Call
import com.exemplo.astroimagemdodia.data.cronet.CronetEngineAdapter
import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity

class NasaServiceImp(
    private val cronetEngineAdapter: CronetEngineAdapter
) : NasaService {

    override fun getImageDay(): Call<ImageDayDataEntity, ImageDayEntity> =
        cronetEngineAdapter.newCallBuilder()
            .method(Call.Method.GET)
            .url("planetary/apod")
            .build()
}