package com.exemplo.astroimagemdodia.data.cronet

import android.os.Handler
import com.exemplo.astroimagemdodia.data.mapper.Mapper
import com.exemplo.astroimagemdodia.domain.callback.Callback

//TODO: Precisa ser um java.util.Observable
class Observable<R, T>(
) : java.util.Observable() {
    private val handler = Handler()

    private lateinit var mapper: Mapper<R, T>

    private lateinit var starter: Starter<R>

    private lateinit var callback: Callback<T>

    fun subscribe(callback: Callback<T>): Observable<R, T> {
        this.callback = callback
        return this
    }

    fun map(mapper: Mapper<R, T>): Observable<R, T> {
        this.mapper = mapper
        return this
    }

    fun starter(starter: Starter<R>): Observable<R, T> {
        this.starter = starter
        return this
    }

    fun execute(): Observable<R, T> {
        starter.start()

        return this
    }

    fun onFailure(throwable: Throwable) {
        handler.post {
            callback.error(throwable)
        }
    }

    fun onResponse(response: R) {
        handler.post {
            callback.success(mapper.execute(response))
        }
    }
}