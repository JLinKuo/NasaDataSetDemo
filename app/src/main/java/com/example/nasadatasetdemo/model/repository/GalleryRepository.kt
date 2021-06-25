package com.example.nasadatasetdemo.model.repository

import com.example.nasadatasetdemo.model.network.Network

class GalleryRepository: BaseRepository() {
    fun getNasaHttpRequest(): String {
        return Network.httpGetString()
    }
 }