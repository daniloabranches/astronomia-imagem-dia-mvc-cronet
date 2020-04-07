package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.cronet.CronetEngineAdapter
import com.exemplo.astroimagemdodia.data.cronet.Observable
import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity

class NasaServiceImp(
    private val cronetEngineAdapter: CronetEngineAdapter
) : NasaService {
    override fun getImageDay(): Observable<ImageDayDataEntity> {
        return cronetEngineAdapter.prepare(CronetEngineAdapter.Method.GET, "planetary/apod")
    }
}