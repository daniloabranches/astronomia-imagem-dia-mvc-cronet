package com.exemplo.astroimagemdodia.data.cronet

import org.chromium.net.CronetEngine

class CronetEngineAdapter(
    private val cronetEngine: CronetEngine
) {
    var baseUrl = ""
    private var queryParameters = ""

    fun newCallBuilder(): CallImp.CallBuilder {
        return CallImp.CallBuilder(cronetEngine, baseUrl, queryParameters)
    }

    fun addQueryParameter(parameter: String, value: String) {
        if (parameter.isBlank()) throw Exception("parameter cannot be empty")
        if (value.isBlank()) throw Exception("value cannot be empty")

        if (queryParameters.isNotEmpty()) {
            queryParameters += "&"
        }

        queryParameters += "$parameter=$value"
    }
}