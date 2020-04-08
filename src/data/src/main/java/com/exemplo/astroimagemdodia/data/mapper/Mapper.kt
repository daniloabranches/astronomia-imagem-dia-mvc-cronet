package com.exemplo.astroimagemdodia.data.mapper

interface Mapper<R, T> {
    fun execute(data: R): T
}