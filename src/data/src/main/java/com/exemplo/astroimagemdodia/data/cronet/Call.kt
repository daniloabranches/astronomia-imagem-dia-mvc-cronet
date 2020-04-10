package com.exemplo.astroimagemdodia.data.cronet

import com.exemplo.astroimagemdodia.domain.core.Callback

interface Call<R, T> {
    interface Method {
        companion object {
            val GET = "GET"
            val HEAD = "HEAD"
            val DELETE = "DELETE"
            val POST = "POST"
            val PUT = "PUT"
        }
    }

    fun callback(callback: Callback<T>): Call<R, T>
    fun map(mapper: Mapper<R, T>): Call<R, T>
    fun execute(): Call<R, T>
    fun error(throwable: Throwable)
    fun success(data: R)
}