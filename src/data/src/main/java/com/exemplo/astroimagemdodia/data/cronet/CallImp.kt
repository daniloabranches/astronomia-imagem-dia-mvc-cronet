package com.exemplo.astroimagemdodia.data.cronet

import android.os.Handler
import com.exemplo.astroimagemdodia.domain.core.Callback
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import java.util.concurrent.Executors

class CallImp<R, T>(
    private val clazz: Class<R>,
    private val cronetEngine: CronetEngine,
    private val url: String,
    private val method: String?
) : Call<R, T> {
    private val handler = Handler()
    private lateinit var request: UrlRequest
    private lateinit var mapper: Mapper<R, T>
    private var callback: Callback<T>? = null

    override fun callback(callback: Callback<T>): Call<R, T> {
        this.callback = callback
        return this
    }

    override fun map(mapper: Mapper<R, T>): Call<R, T> {
        this.mapper = mapper
        return this
    }

    override fun execute(): Call<R, T> {
        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            url,
            GsonUrlRequestCallback(clazz, this),
            Executors.newSingleThreadExecutor()
        )

        if (method != null) {
            requestBuilder.setHttpMethod(method)
        }

        request = requestBuilder.build()
        request.start()

        return this
    }

    override fun error(throwable: Throwable) {
        handler.post { callback?.error(throwable) }
    }

    override fun success(data: R) {
        handler.post { callback?.success(mapper.execute(data)) }
    }

    class CallBuilder(
        val cronetEngine: CronetEngine,
        private val baseUrl: String,
        private val queryParameters: String
    ) {
        var url: String? = null
            set(value) {
                if (value == null) throw Exception("url cannot be null")
                if (value.isBlank()) throw Exception("url cannot be empty")

                val newUrl = "$baseUrl$value"

                field = if (newUrl.contains('?')) {
                    "$newUrl&$queryParameters"
                } else {
                    "$newUrl?$queryParameters"
                }
            }

        var method: String? = null
            set(value) {
                if (value?.isBlank() == true) throw Exception("method cannot be empty")
                field = value
            }

        fun method(method: String): CallBuilder {
            this.method = method
            return this
        }

        fun url(url: String): CallBuilder {
            this.url = url
            return this
        }

        inline fun <reified R, T> build(): Call<R, T> {
            if (url == null) throw Exception("url cannot be null")

            return CallImp(R::class.java, cronetEngine, url!!, method)
        }
    }
}