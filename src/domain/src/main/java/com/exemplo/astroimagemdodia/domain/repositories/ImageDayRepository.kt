package com.exemplo.astroimagemdodia.domain.repositories

import com.exemplo.astroimagemdodia.domain.core.Callback
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity

interface ImageDayRepository {
    fun getImageDay(callback: Callback<ImageDayEntity>)
}