package com.exemplo.astroimagemdodia.data.repositories

import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.data.mapper.Mapper
import com.exemplo.astroimagemdodia.data.services.NasaService
import com.exemplo.astroimagemdodia.domain.callback.Callback
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository

class ImageDayDataRepository(
    private val nasaService: NasaService
) : ImageDayRepository {

    private val mapper = object :
        Mapper<ImageDayDataEntity, ImageDayEntity> {
        override fun execute(data: ImageDayDataEntity): ImageDayEntity {
            return ImageDayEntity(
                data.Date,
                data.Explanation,
                data.HDUrl,
                data.MediaType,
                data.Title,
                data.URL
            )
        }
    }

    override fun getImageDay(callback: Callback<ImageDayEntity>) {
        nasaService.getImageDay()
            .subscribe(callback)
            .map(mapper)
            .execute()
    }
}