package com.exemplo.astroimagemdodia.data.volley

import android.content.Context
import android.os.Build
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.util.*

class VolleyAdapter(
    context: Context,
    private val baseUrl: String,
    private val queryParameters: String
) {
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    fun getUrl(resource: String): String {
        val url = "$baseUrl$resource"

        return if (url.contains('?')) {
            "$url&$queryParameters"
        } else {
            "$url?$queryParameters"
        }
    }

    inline fun <reified T> enqueue(method: Int, url: String): Observable<T> {
        return Observable(
            this,
            getUrl(url),
            method,
            T::class.java
        )
    }

    class Builder(private val context: Context) {
        private var baseUrl: String = ""
        private var queryParameters: String = ""

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun addQueryParameter(parameter: String, value: String): Builder {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(parameter)
                Objects.requireNonNull(value)
            }

            if (queryParameters.isNotEmpty()) {
                queryParameters += "&"
            }

            queryParameters += "$parameter=$value"

            return this
        }

        fun build(): VolleyAdapter {
            return VolleyAdapter(context, baseUrl, queryParameters)
        }
    }
}