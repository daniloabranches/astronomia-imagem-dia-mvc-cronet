package com.exemplo.astroimagemdodia.data.mapper

interface Mapper<T> {
    fun execute(data: T?): Any?
}