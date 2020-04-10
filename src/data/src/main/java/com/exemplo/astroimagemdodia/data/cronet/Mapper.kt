package com.exemplo.astroimagemdodia.data.cronet

interface Mapper<R, T> {
    fun execute(data: R): T
}