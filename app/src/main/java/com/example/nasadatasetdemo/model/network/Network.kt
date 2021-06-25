package com.example.nasadatasetdemo.model.network

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val NASA_DATA_URL = "https://raw.githubusercontent.com/cmmobile/NasaDataSet/main/apod.json"

object Network {
    fun httpGetString(): String {
        var inputStream: InputStream? = null
        var result = ""

        try {
            val url = URL(NASA_DATA_URL)
            val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            conn.connect()
            inputStream = conn.inputStream

            return inputStream?.bufferedReader()?.use(BufferedReader::readText) ?: "error: inputStream is null"

        } catch(ex: Exception) {
            Log.d("JLin", "exception @ httpGetString: " + ex.message)

        } finally {
            inputStream?.close()

        }

        return result
    }
}