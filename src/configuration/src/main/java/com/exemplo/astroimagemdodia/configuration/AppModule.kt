package com.exemplo.astroimagemdodia.configuration

import android.content.Context
import com.exemplo.astroimagemdodia.data.cronet.CronetEngineAdapter
import com.exemplo.astroimagemdodia.data.repositories.ImageDayDataRepository
import com.exemplo.astroimagemdodia.data.services.ConfigService
import com.exemplo.astroimagemdodia.data.services.NasaServiceImp
import com.exemplo.astroimagemdodia.domain.repositories.ImageDayRepository
import com.exemplo.astroimagemdodia.domain.usecases.GetImageDayUseCase
import org.chromium.net.CronetEngine

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
        val cronetEngine = CronetEngine.Builder(context.applicationContext).build()

        CronetEngineAdapter(cronetEngine).also {
            it.baseUrl = configService.getApiUrl()
            it.addQueryParameter("api_key", configService.getApiKey())
        }
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