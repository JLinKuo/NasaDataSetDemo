package com.example.nasadatasetdemo.view.main

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.nasadatasetdemo.R
import com.example.nasadatasetdemo.view.base.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelFactory() }

    private val progressDialog by lazy {
        AlertDialog.Builder(this).setView(R.layout.view_progress_dialog).create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOrientationPortrait()
    }

    private fun setOrientationPortrait() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }

    fun showProgressBar(isCancelable: Boolean) {
        progressDialog.setCancelable(isCancelable)
        progressDialog.show()
    }

    fun dismissProgressBar() {
        if(progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}