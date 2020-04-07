package com.exemplo.astroimagemdodia.configuration

import android.content.Context
import com.exemplo.astroimagemdodia.data.cronet.CronetEngineAdapter
import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.data.services.ConfigService
import com.exemplo.astroimagemdodia.data.services.NasaServiceImp
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase
import java.util.concurrent.Executors

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

    private val cronetEngineAdapter by lazy {
        val configService = ConfigService()

        CronetEngineAdapter.Builder(context.applicationContext)
            .baseUrl(configService.getApiUrl())
            .executor(Executors.newSingleThreadExecutor())
            .addQueryParameter("api_key", configService.getApiKey())
            .build()
    }

    private val nasaService by lazy {
        NasaServiceImp(cronetEngineAdapter)
    }

    private val imageDayDataRepository: ImageDayRepository by lazy {
        ImageDayDataRepository(nasaService)
    }

    private val getImageDayUseCase by lazy {
        GetImageDayUseCase(imageDayDataRepository)
    }

    fun getImageDayUseCase() = getImageDayUseCase
}