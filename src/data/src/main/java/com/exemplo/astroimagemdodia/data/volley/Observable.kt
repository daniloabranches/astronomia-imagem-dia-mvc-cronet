package com.exemplo.astroimagemdodia.data.volley

import com.android.volley.Response
import com.exemplo.astroimagemdodia.data.mapper.Mapper
import java.util.*

class Observable<R>(
    private val volleyAdapter: VolleyAdapter,
    private val url: String,
    private val method: Int,
    private val clazz: Class<R>
) : java.util.Observable() {

    private var mapper: Mapper<R> = object : Mapper<R> {
        override fun execute(data: R?): Any? = data
    }

    fun subscribe(observer: Observer): Observable<R> {
        addObserver(observer)
        return this
    }

    fun map(mapper: Mapper<R>): Observable<R> {
        this.mapper = mapper
        return this
    }

    fun execute(): Observable<R> {
        val request = GsonRequest(url, method, clazz, null,
            Response.Listener { response ->
                onResponse(response)
            },
            Response.ErrorListener { error ->
                onFailure(error)
            })

        volleyAdapter.addToRequestQueue(request)

        return this
    }

    private fun onFailure(throwable: Throwable) = setData(throwable)

    private fun onResponse(response: R) {
        setData(mapper.execute(response))
    }

    private fun setData(data: Any?) {
        setChanged()
        notifyObservers(data)
    }
}