package com.example.nasadatasetdemo.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadatasetdemo.databinding.FragmentGalleryBinding
import com.example.nasadatasetdemo.view.base.BaseFragment
import com.example.nasadatasetdemo.view.pojo.NasaItemPojo

/**
 * A simple [Fragment] subclass.
 */
private const val DEFAULT_BITMAP_AMOUNT = 48

class GalleryFragment : BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    private val listAdapter by lazy { GalleryAdapter(
            object: GalleryAdapter.ItemSelectListener{
        override fun onItemSelected(position: Int) {
            Toast.makeText(activity, "position: $position", Toast.LENGTH_SHORT).show()
        }
    }) }
    private val listNasaData by lazy { ArrayList<NasaItemPojo>() }

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
        viewModel.getNasaData()
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
                        lastVisibleItemPosition <= listNasaData.size) {

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
            if(!listNasaData[currBitmapIndex].isLoadingBitmap) {
                listNasaData[currBitmapIndex].isLoadingBitmap = true
                viewModel.getNasaBitmap(currBitmapIndex,
                        listNasaData[currBitmapIndex].thumbnailUrl)
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
        viewModel.getNasaDataResponse.observe(viewLifecycleOwner) {
            activity.dismissProgressBar()
            listNasaData.clear()
            listNasaData.addAll(it)

            listAdapter.setListNasaData(listNasaData)
            getNasaBitmap(0, DEFAULT_BITMAP_AMOUNT)   // 先取得前48筆資料
        }

        viewModel.getNasaBitmapResponse.observe(viewLifecycleOwner) {
            it.bitmap?.let { bitmap ->
                listNasaData[it.position].thumbnailBitmap = bitmap
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