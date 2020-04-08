package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.data.cronet.Observable
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity

interface NasaService {
    fun getImageDay(): Observable<ImageDayDataEntity, ImageDayEntity>
}