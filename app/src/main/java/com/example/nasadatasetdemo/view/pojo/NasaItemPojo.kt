package com.example.nasadatasetdemo.view.pojo

import android.graphics.Bitmap

data class NasaItemPojo(
    val description: String,
    val copyright: String,
    val title: String,
    val thumbnailUrl: String,
    val apod_site: String,
    val date: String,
    val media_type: String,
    val hdUrl: String,

    var thumbnailBitmap: Bitmap? = null
)
