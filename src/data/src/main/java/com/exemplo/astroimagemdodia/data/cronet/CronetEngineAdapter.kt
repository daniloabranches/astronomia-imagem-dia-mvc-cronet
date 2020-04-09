package com.exemplo.astroimagemdodia.data.cronet

import com.exemplo.astroimagemdodia.domain.core.Starter
import org.chromium.net.CronetEngine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CronetEngineAdapter(
    val cronetEngine: CronetEngine,
    private val baseUrl: String,
    val executor: ExecutorService,
    private val queryParameters: String
) {
    interface Method {
        companion object {
            val GET = "GET"
            val HEAD = "HEAD"
            val DELETE = "DELETE"
            val POST = "POST"
            val PUT = "PUT"
        }
    }

    inline fun <reified R, T> createCaller(method: String, url: String): CallerImp<R, T> {
        val caller = CallerImp<R, T>()

        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            prepareUrl(url),
            GsonUrlRequestCallback(R::class.java, caller),
            executor
        )

        val request = requestBuilder
            .setHttpMethod(method)
            .build()

        //TODO: caller precisa do request e request precisa do caller
        caller.starter(object :
            Starter<R> {
            override fun start() {
                request.start()
            }
        })

        return caller
    }

    fun prepareUrl(resource: String): String {
        val url = "$baseUrl$resource"

        return if (url.contains('?')) {
            "$url&$queryParameters"
        } else {
            "$url?$queryParameters"
        }
    }

    class Builder(private val cronetEngine: CronetEngine) {
        private var baseUrl: String = ""
        private var queryParameters: String = ""
        private var executor: ExecutorService? = null

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun executor(executor: ExecutorService): Builder {
            this.executor = executor
            return this
        }

        fun addQueryParameter(parameter: String, value: String): Builder {
            if (queryParameters.isNotEmpty()) {
                queryParameters += "&"
            }

            queryParameters += "$parameter=$value"

            return this
        }

        fun build(): CronetEngineAdapter {
            val executor = executor ?: Executors.newSingleThreadExecutor()

            return CronetEngineAdapter(cronetEngine, baseUrl, executor, queryParameters)
        }
    }
}