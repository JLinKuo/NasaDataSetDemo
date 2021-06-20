package com.example.nasadatasetdemo.view.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected var TAG: String = javaClass.simpleName
}