package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.data.cronet.CallerImp
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity

interface NasaService {
    fun getImageDay(): CallerImp<ImageDayDataEntity, ImageDayEntity>
}