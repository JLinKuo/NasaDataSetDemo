package com.example.nasadatasetdemo.model.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val NASA_DATA_URL = "https://raw.githubusercontent.com/cmmobile/NasaDataSet/main/apod.json"

object Network {
    fun httpGetString(): String {
        httpConnect(NASA_DATA_URL)?.let {
            return it.bufferedReader().use(BufferedReader::readText).apply {
                it.close()
            }
        }
        return "error: inputStream is null"
    }

    fun httpGetBitmap(strUrl: String): Bitmap? {
        httpConnect(strUrl)?.let {
            return BitmapFactory.decodeStream(it).apply {
                it.close()
            }
        }
        return null
    }

    private fun httpConnect(strUrl: String): InputStream? {
        var inputStream: InputStream? = null

        try {
            val conn: HttpsURLConnection = URL(strUrl).openConnection() as HttpsURLConnection
            conn.connect()
            inputStream = conn.inputStream
        } catch (ex: Exception) {
            Log.d("JLin", "Error when executing get request: " + ex.message)
        }

        return inputStream
    }
}