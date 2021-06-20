package com.example.nasadatasetdemo.view.main

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nasadatasetdemo.R
import com.example.nasadatasetdemo.view.base.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOrientationPortrait()

        Log.d("JLin", "main view model: $viewModel")
    }

    private fun setOrientationPortrait() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }
}