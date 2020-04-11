package com.exemplo.astroimagemdodia.data.cronet

import com.google.gson.Gson
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.WritableByteChannel

class GsonUrlRequestCallback<R, T>(
    private val clazz: Class<R>,
    private val call: CallImp<R, T>
) : UrlRequest.Callback() {
    private val bytesReceived: ByteArrayOutputStream = ByteArrayOutputStream()
    private val receiveChannel: WritableByteChannel = Channels.newChannel(bytesReceived)

    override fun onRedirectReceived(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        newLocationUrl: String?
    ) {
        request?.followRedirect()
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        request?.read(ByteBuffer.allocateDirect(32 * 1024))
    }

    override fun onReadCompleted(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        byteBuffer: ByteBuffer?
    ) {
        byteBuffer!!.flip()

        try {
            receiveChannel.write(byteBuffer)
        } catch (e: IOException) {

        }

        byteBuffer.clear()
        request!!.read(byteBuffer)
    }

    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
        call.error(error ?: Throwable("unknown error"))
    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        try {
            val byteArray = bytesReceived.toByteArray()
            val data = parseJson(byteArray)
            call.success(data)
        } catch (error: Exception) {
            call.error(error)
        }
    }

    private fun parseJson(byteArray: ByteArray): R {
        val json = String(byteArray)
        return Gson().fromJson(json, clazz)
    }
}