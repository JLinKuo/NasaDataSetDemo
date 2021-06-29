package com.example.nasadatasetdemo.view.gallery

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nasadatasetdemo.model.repository.GalleryRepository
import com.example.nasadatasetdemo.view.base.BaseViewModel
import com.example.nasadatasetdemo.model.pojo.NasaBitmapPojo
import com.example.nasadatasetdemo.model.pojo.NasaItemPojo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

private const val JSON_KEY_DESCRIPTION = "description"
private const val JSON_KEY_COPYRIGHT = "copyright"
private const val JSON_KEY_TITLE = "title"
private const val JSON_KEY_THUMBNAIL_URL = "url"
private const val JSON_KEY_APOD_SITE = "apod_site"
private const val JSON_KEY_DATE = "date"
private const val JSON_KEY_MEDIA_TYPE = "media_type"
private const val JSON_KEY_HD_URL = "hdurl"

class GalleryViewModel: BaseViewModel() {
    private val repository = GalleryRepository()

    val listNasaData by lazy { ArrayList<NasaItemPojo>() }
    var isGetNasaData = false

    private val _getNasaDataResponse = MutableLiveData<ArrayList<NasaItemPojo>>()
    val getNasaDataResponse: LiveData<ArrayList<NasaItemPojo>>
        get() = _getNasaDataResponse

    fun getNasaData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNasaHttpRequest()

            withContext(Dispatchers.Main) {
                _getNasaDataResponse.value = convertString2List(result)
            }
        }
    }

    private fun convertString2List(result: String?): ArrayList<NasaItemPojo> {
        return ArrayList<NasaItemPojo>().apply {
            if (!result.isNullOrBlank()) {
                val jsonArray = JSONArray(result)

                for (i in 0 until jsonArray.length()) {
                    val dataJsonObject = jsonArray.getJSONObject(i)

                    this.add(
                        NasaItemPojo(
                            description = dataJsonObject.getString(JSON_KEY_DESCRIPTION),
                            copyright = dataJsonObject.getString(JSON_KEY_COPYRIGHT),
                            title = dataJsonObject.getString(JSON_KEY_TITLE),
                            thumbnailUrl = dataJsonObject.getString(JSON_KEY_THUMBNAIL_URL),
                            apod_site = dataJsonObject.getString(JSON_KEY_APOD_SITE),
                            date = dataJsonObject.getString(JSON_KEY_DATE),
                            media_type = dataJsonObject.getString(JSON_KEY_MEDIA_TYPE),
                            hdUrl = dataJsonObject.getString(JSON_KEY_HD_URL)
                        )
                    )
                }
            }
        }
    }

    private val _getNasaBitmapResponse = MutableLiveData<NasaBitmapPojo>()
    val getNasaBitmapResponse: LiveData<NasaBitmapPojo>
        get() = _getNasaBitmapResponse

    fun getNasaBitmap(position: Int, urlString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNasaHttpBitmap(urlString)

            withContext(Dispatchers.Main) {
                _getNasaBitmapResponse.value = NasaBitmapPojo(position, resizeResultBitmap(result))
            }
        }
    }

    private fun resizeResultBitmap(bitmap: Bitmap?): Bitmap? {
        bitmap?.let {
            val scaleSize = (it.width * 0.1).toInt()
            return Bitmap.createScaledBitmap(it, scaleSize, scaleSize, true)
        }

        return null
    }
}