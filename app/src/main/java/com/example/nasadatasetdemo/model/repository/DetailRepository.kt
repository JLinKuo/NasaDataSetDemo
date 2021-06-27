package com.example.nasadatasetdemo.model.repository

import android.graphics.Bitmap
import com.example.nasadatasetdemo.model.network.Network

class DetailRepository: BaseRepository() {
    fun getNasaHttpBitmap(urlString: String): Bitmap? {
        return Network.httpGetBitmap(urlString)
    }
}