package com.exemplo.astroimagemdodia.data.cronet

import android.content.Context
import org.chromium.net.CronetEngine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CronetEngineAdapter(
    context: Context,
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

    val cronetEngine: CronetEngine by lazy {
        CronetEngine.Builder(context).build()
    }

    inline fun <reified R, T> prepare(method: String, url: String): Observable<R, T> {
        val observable = Observable<R, T>()

        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            prepareUrl(url),
            //TODO: GsonUrlRequestCallback depende de observable
            GsonUrlRequestCallback(R::class.java, observable),
            executor
        )

        val request = requestBuilder
            .setHttpMethod(method)
            .build()

        //TODO: observable depende de request
        observable.starter(object : Starter<R> {
            override fun start() {
                request.start()
            }
        })

        return observable
    }

    fun prepareUrl(resource: String): String {
        val url = "$baseUrl$resource"

        return if (url.contains('?')) {
            "$url&$queryParameters"
        } else {
            "$url?$queryParameters"
        }
    }

    class Builder(private val context: Context) {
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

            return CronetEngineAdapter(context, baseUrl, executor, queryParameters)
        }
    }
}