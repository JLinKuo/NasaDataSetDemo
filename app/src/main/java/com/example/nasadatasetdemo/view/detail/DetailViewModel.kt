package com.example.nasadatasetdemo.view.detail

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nasadatasetdemo.model.repository.DetailRepository
import com.example.nasadatasetdemo.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: BaseViewModel() {
    private val repository = DetailRepository()

    private val _getNasaBitmapResponse = MutableLiveData<Bitmap>()
    val getNasaBitmapResponse: LiveData<Bitmap>
        get() = _getNasaBitmapResponse

    fun getNasaBitmap(urlString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNasaHttpBitmap(urlString)

            withContext(Dispatchers.Main) {
                _getNasaBitmapResponse.value = result
            }
        }
    }
}