package com.example.nasadatasetdemo.view.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaItemBean(
    val description: String,
    val copyright: String,
    val title: String,
    val apod_site: String,
    val date: String,
    val media_type: String,
    val hdUrl: String
): Parcelable
