package com.exemplo.astroimagemdodia.domain.usecases

import com.exemplo.astroimagemdodia.domain.core.Callback

interface UseCase<T> {
    fun execute(callback: Callback<T>)
}