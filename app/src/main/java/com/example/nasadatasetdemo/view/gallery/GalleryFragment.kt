package com.example.nasadatasetdemo.view.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadatasetdemo.databinding.FragmentGalleryBinding
import com.example.nasadatasetdemo.view.base.BaseFragment
import com.example.nasadatasetdemo.view.bean.NasaItemBean

/**
 * A simple [Fragment] subclass.
 */
private const val DEFAULT_BITMAP_AMOUNT = 48

class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    private val listAdapter by lazy { GalleryAdapter(
            object: GalleryAdapter.ItemSelectListener{
        override fun onItemSelected(position: Int) {
            findNavController().navigate(
                GalleryFragmentDirections.actionGalleryFragmentToDetailFragment(
                    NasaItemBean(
                        description = viewModel.listNasaData[position].description,
                        copyright = viewModel.listNasaData[position].copyright,
                        title = viewModel.listNasaData[position].title,
                        apod_site = viewModel.listNasaData[position].apod_site,
                        date = viewModel.listNasaData[position].date,
                        media_type = viewModel.listNasaData[position].media_type,
                        hdUrl = viewModel.listNasaData[position].hdUrl
                    )
                )
            )
        }
    }) }

    private var currBitmapIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNasaData()
        setView()
        setListener()
        setObserver()
    }

    private fun setView() {
        setRecyclerView()
    }

    private fun getNasaData() {
        activity.showProgressBar(true)
        if(!viewModel.isGetNasaData) {
            viewModel.getNasaData()
        }
    }

    private fun setRecyclerView() {
        val layoutManager = GridLayoutManager(activity, 4)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = listAdapter
        binding.recyclerview.addItemDecoration(GalleryItemDecoration(activity))

        binding.recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            private var firstVisibleItemPosition = 0
            private var lastVisibleItemPosition = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItemPosition <= viewModel.listNasaData.size) {

                    getNasaBitmap(firstVisibleItemPosition, lastVisibleItemPosition)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }
        })
    }

    private fun getNasaBitmap(firstIndex: Int, lastIndex: Int) {
        currBitmapIndex = firstIndex
        while(currBitmapIndex <= lastIndex) {
            if(!viewModel.listNasaData[currBitmapIndex].isLoadingBitmap) {
                viewModel.listNasaData[currBitmapIndex].isLoadingBitmap = true
                viewModel.getNasaBitmap(currBitmapIndex,
                    viewModel.listNasaData[currBitmapIndex].thumbnailUrl)
            }

            currBitmapIndex += 1
        }
    }

    private fun setListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObserver() {
        setNasaDataResponseObserver()
        setNasaBitmapResponseObserver()
    }

    private fun setNasaDataResponseObserver() {
        viewModel.getNasaDataResponse.observe(viewLifecycleOwner) {
            viewModel.isGetNasaData = true
            activity.dismissProgressBar()
            viewModel.listNasaData.clear()
            viewModel.listNasaData.addAll(it)

            listAdapter.setListNasaData(viewModel.listNasaData)
            getNasaBitmap(0, DEFAULT_BITMAP_AMOUNT)   // ????????????48?????????
        }
    }

    private fun setNasaBitmapResponseObserver() {
        viewModel.getNasaBitmapResponse.observe(viewLifecycleOwner) {
            it.bitmap?.let { bitmap ->
                viewModel.listNasaData[it.position].thumbnailBitmap = bitmap
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