package com.exemplo.astroimagemdodia.domain.usecases

import com.exemplo.astroimagemdodia.domain.core.Callback
import com.exemplo.astroimagemdodia.domain.entities.ImageDayEntity
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository

class GetImageDayUseCase(private val imageDayRepository: ImageDayRepository) :
    UseCase<ImageDayEntity> {

    override fun execute(callback: Callback<ImageDayEntity>) =
        imageDayRepository.getImageDay(callback)
}