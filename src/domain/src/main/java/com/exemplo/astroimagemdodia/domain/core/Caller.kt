package com.exemplo.astroimagemdodia.domain.core

interface Caller<R, T> {
    fun subscribe(callback: Callback<T>): Caller<R, T>
    fun map(mapper: Mapper<R, T>): Caller<R, T>
    fun starter(starter: Starter<R>): Caller<R, T>
    fun execute(): Caller<R, T>
    fun error(throwable: Throwable)
    fun success(data: R)
}