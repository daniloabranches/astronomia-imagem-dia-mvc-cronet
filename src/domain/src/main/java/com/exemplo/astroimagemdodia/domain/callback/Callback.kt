package com.exemplo.astroimagemdodia.domain.callback

//TODO: Definir melhor pacote
interface Callback<T> {
    fun success(data: T)
    fun error(error: Throwable)
}