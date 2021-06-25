package com.example.nasadatasetdemo.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasadatasetdemo.databinding.FragmentGalleryBinding
import com.example.nasadatasetdemo.view.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNasaData()
        setObserver()
    }

    private fun getNasaData() {
        activity.showProgressBar(true)
        viewModel.getNasaData()
    }

    private fun setObserver() {
        viewModel.getNasaDataResponse.observe(viewLifecycleOwner) {
            activity.dismissProgressBar()
            Log.d("JLin", "第一筆: ${it[0].hdUrl}")
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGalleryBinding.inflate(inflater, container, false)

    override fun getViewModel() = GalleryViewModel::class.java
}