package com.exemplo.astroimagemdodia.configuration

import android.content.Context
import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.data.services.ConfigService
import com.exemplo.astroimagemdodia.data.services.NasaServiceImp
import com.exemplo.astroimagemdodia.data.volley.VolleyAdapter
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase

class AppModule(context: Context) {
    companion object {
        @Volatile
        private var appModule: AppModule? = null
        fun getInstance(context: Context) =
            appModule ?: synchronized(this) {
                appModule ?: AppModule(context).also {
                    appModule = it
                }
            }
    }

    private val volleyAdapter by lazy {
        val configService = ConfigService()

        VolleyAdapter.Builder(context)
            .baseUrl(configService.getApiUrl())
            .addQueryParameter("api_key", configService.getApiKey())
            .build()
    }

    private val nasaService by lazy {
        NasaServiceImp(volleyAdapter)
    }

    private val imageDayDataRepository: ImageDayRepository by lazy {
        ImageDayDataRepository(nasaService)
    }

    private val getImageDayUseCase by lazy {
        GetImageDayUseCase(imageDayDataRepository)
    }

    fun getImageDayUseCase() = getImageDayUseCase
}