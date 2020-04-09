package com.exemplo.astroimagemdodia.data.cronet

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.WritableByteChannel

class GsonUrlRequestCallback<R, T>(
    private val clazz: Class<R>,
    private val caller: CallerImp<R, T>
) : UrlRequest.Callback() {

    private val bytesReceived: ByteArrayOutputStream = ByteArrayOutputStream()
    private val receiveChannel: WritableByteChannel = Channels.newChannel(bytesReceived)
    private val gson = Gson()

    override fun onRedirectReceived(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        newLocationUrl: String?
    ) {
        //TODO: Rever isso aqui

        request?.followRedirect()
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        //TODO: Rever isso aqui

        //request?.read(ByteBuffer.allocateDirect(102400))
        request?.read(ByteBuffer.allocateDirect(32 * 1024))
    }

    override fun onReadCompleted(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        byteBuffer: ByteBuffer?
    ) {
        //TODO: Rever isso aqui

        byteBuffer!!.flip()
        try {
            receiveChannel.write(byteBuffer)
        } catch (e: IOException) {

        }

        byteBuffer!!.clear()
        request!!.read(byteBuffer)
    }

    override fun onFailed(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        error: CronetException?
    ) {
        //TODO: Rever isso aqui

        caller.error(error ?: Throwable("ERRO"))
    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        //TODO: Rever isso aqui

        val byteArray = bytesReceived.toByteArray()

        try {
            val data = parseJson(byteArray)
            caller.success(data)
        } catch (error: UnsupportedEncodingException) {
            caller.error(error)
        } catch (error: JsonSyntaxException) {
            caller.error(error)
        }
    }

    private fun parseJson(byteArray: ByteArray): R {
        val json = String(byteArray)
        return gson.fromJson(json, clazz)
    }
}