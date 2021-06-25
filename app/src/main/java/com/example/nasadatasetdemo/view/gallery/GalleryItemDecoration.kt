package com.example.nasadatasetdemo.view.gallery

import android.app.Activity
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadatasetdemo.R

class GalleryItemDecoration(private val activity: Activity): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            outRect.top = activity.resources.getDimension(R.dimen.gallery_item_decoration_separate_space).toInt()
            outRect.bottom = activity.resources.getDimension(R.dimen.gallery_item_decoration_separate_space).toInt()
            outRect.left = activity.resources.getDimension(R.dimen.gallery_item_decoration_separate_space).toInt()
            outRect.right = activity.resources.getDimension(R.dimen.gallery_item_decoration_separate_space).toInt()
        }
    }
}