package com.example.nasadatasetdemo.model.pojo

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

    var isLoadingBitmap: Boolean = false,
    var thumbnailBitmap: Bitmap? = null
)
