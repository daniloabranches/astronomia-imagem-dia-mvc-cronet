package com.exemplo.astroimagemdodia.data.cronet

import android.os.Handler
import com.exemplo.astroimagemdodia.domain.core.Callback
import com.exemplo.astroimagemdodia.domain.core.Caller
import com.exemplo.astroimagemdodia.domain.core.Mapper
import com.exemplo.astroimagemdodia.domain.core.Starter

class CallerImp<R, T> : Caller<R, T> {
    private val handler = Handler()
    private lateinit var mapper: Mapper<R, T>
    private lateinit var starter: Starter<R>
    private lateinit var callback: Callback<T>

    override fun subscribe(callback: Callback<T>): Caller<R, T> {
        this.callback = callback
        return this
    }

    override fun map(mapper: Mapper<R, T>): Caller<R, T> {
        this.mapper = mapper
        return this
    }

    override fun starter(starter: Starter<R>): Caller<R, T> {
        this.starter = starter
        return this
    }

    override fun execute(): Caller<R, T> {
        starter.start()
        return this
    }

    override fun error(throwable: Throwable) {
        handler.post { callback.error(throwable) }
    }

    override fun success(data: R) {
        handler.post { callback.success(mapper.execute(data)) }
    }
}