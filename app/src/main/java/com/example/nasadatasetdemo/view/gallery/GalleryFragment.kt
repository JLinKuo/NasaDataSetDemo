package com.example.nasadatasetdemo.view.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasadatasetdemo.databinding.FragmentGalleryBinding
import com.example.nasadatasetdemo.view.base.BaseFragment
import com.example.nasadatasetdemo.view.pojo.NasaItemPojo

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    private lateinit var listAdapter: GalleryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNasaData()
        setObserver()
    }

    private fun getNasaData() {
        activity.showProgressBar(true)
        viewModel.getNasaData()
    }

    private fun setRecyclerView(list: List<NasaItemPojo>) {
        binding.recyclerview.layoutManager = GridLayoutManager(activity, 4)
        listAdapter = GalleryAdapter(list)
        binding.recyclerview.adapter = listAdapter
        binding.recyclerview.addItemDecoration(GalleryItemDecoration(activity))

        for(i in list.indices) {
            viewModel.getNasaBitmap(i, list[i].thumbnailUrl)
        }
    }

    private fun setObserver() {
        viewModel.getNasaDataResponse.observe(viewLifecycleOwner) {
            activity.dismissProgressBar()

            setRecyclerView(it)
        }

        viewModel.getNasaBitmapResponse.observe(viewLifecycleOwner) {
            it.bitmap?.let { bitmap ->
                listAdapter.setItemBitmap(it.position, bitmap)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGalleryBinding.inflate(inflater, container, false)

    override fun getViewModel() = GalleryViewModel::class.java
}