package com.example.nasadatasetdemo.model.repository

import com.example.nasadatasetdemo.model.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepository: BaseRepository() {
    suspend fun getNasaHttpBitmap(urlString: String) = withContext(Dispatchers.IO) {
        Network.httpGetBitmap(urlString)
    }
}