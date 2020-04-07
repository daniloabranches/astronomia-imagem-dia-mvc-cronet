package com.exemplo.astroimagemdodia.data.cronet

import android.os.Handler
import com.exemplo.astroimagemdodia.data.mapper.Mapper
import java.util.*

class Observable<R>(
) : java.util.Observable() {
    private val handler = Handler()

    private var mapper: Mapper<R> = object : Mapper<R> {
        override fun execute(data: R?): Any? = data
    }

    private var starter: Starter<R> = object : Starter<R> {
        override fun start() {}
    }

    fun subscribe(observer: Observer): Observable<R> {
        addObserver(observer)
        return this
    }

    fun map(mapper: Mapper<R>): Observable<R> {
        this.mapper = mapper
        return this
    }

    fun starter(starter: Starter<R>): Observable<R> {
        this.starter = starter
        return this
    }

    fun execute(): Observable<R> {
        starter.start()

        return this
    }

    fun onFailure(throwable: Throwable) {
        handler.post {
            setData(throwable)
        }
    }

    fun onResponse(response: R) {
        handler.post {
            setData(mapper.execute(response))
        }
    }

    private fun setData(data: Any?) {
        setChanged()
        notifyObservers(data)
    }
}