package com.exemplo.astroimagemdodia.domain.core

interface Callback<T> {
    fun success(data: T)
    fun error(error: Throwable)
}