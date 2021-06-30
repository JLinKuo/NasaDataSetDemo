package com.example.nasadatasetdemo.model.repository

import com.example.nasadatasetdemo.model.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryRepository: BaseRepository() {
    suspend fun getNasaHttpRequest() = withContext(Dispatchers.IO) {
        Network.httpGetString()
    }

    suspend fun getNasaHttpBitmap(urlString: String) = withContext(Dispatchers.IO) {
        Network.httpGetBitmap(urlString)
    }
 }