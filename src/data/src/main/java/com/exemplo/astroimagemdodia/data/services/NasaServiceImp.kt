package com.exemplo.astroimagemdodia.data.services

import com.android.volley.Request
import com.exemplo.astroimagemdodia.data.entities.ImageDayDataEntity
import com.exemplo.astroimagemdodia.data.volley.Observable
import com.exemplo.astroimagemdodia.data.volley.VolleyAdapter

class NasaServiceImp(
    private val volleyAdapter: VolleyAdapter) : NasaService {

    override fun getImageDay(): Observable<ImageDayDataEntity> {
        return volleyAdapter.enqueue(Request.Method.GET, "planetary/apod")
    }
}