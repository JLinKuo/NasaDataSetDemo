package com.example.nasadatasetdemo.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            // Activity

            // All Fragments

            else -> throw IllegalArgumentException("Unknown ViewModel Class.")
        }
    }
}