package com.example.nasadatasetdemo.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.nasadatasetdemo.databinding.FragmentDetailBinding
import com.example.nasadatasetdemo.view.base.BaseFragment
import com.example.nasadatasetdemo.view.bean.NasaItemBean

/**
 * A simple [Fragment] subclass.

 */
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private val args: DetailFragmentArgs by navArgs()
    private val nasaItemBean by lazy { args.nasaItemBean }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("JLin", "nasaItemBean: ${nasaItemBean}")
    }

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun getViewModel() = DetailViewModel::class.java
}