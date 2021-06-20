package com.example.nasadatasetdemo.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasadatasetdemo.view.gallery.GalleryViewModel
import com.example.nasadatasetdemo.view.home.HomeViewModel
import com.example.nasadatasetdemo.view.main.MainViewModel

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            // Activity
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }

            // All Fragments
            modelClass.isAssignableFrom(GalleryViewModel::class.java) -> {
                GalleryViewModel() as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel() as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class.")
        }
    }
}