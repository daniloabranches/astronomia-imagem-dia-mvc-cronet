package com.exemplo.astroimagemdodia.data.services

import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.data.cronet.Observable

interface NasaService {
    fun getImageDay(): Observable<ImageDayDataEntity>
}