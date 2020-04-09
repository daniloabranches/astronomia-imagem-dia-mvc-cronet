package com.exemplo.astroimagemdodia.domain.core

interface Mapper<R, T> {
    fun execute(data: R): T
}