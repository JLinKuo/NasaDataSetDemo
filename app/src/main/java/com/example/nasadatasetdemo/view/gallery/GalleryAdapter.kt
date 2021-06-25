package com.example.nasadatasetdemo.view.gallery

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadatasetdemo.databinding.ViewListGalleryItemBinding
import com.example.nasadatasetdemo.view.pojo.NasaItemPojo

class GalleryAdapter(
    private val list: List<NasaItemPojo>
): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ViewListGalleryItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = list[position].title

        list[position].thumbnailBitmap?.let {
            holder.binding.image.setImageBitmap(it)
        }
    }

    override fun getItemCount() = list.size

    fun setItemBitmap(position: Int, bitmap: Bitmap) {
        list[position].thumbnailBitmap = bitmap
        notifyItemChanged(position)
    }

    inner class ViewHolder(val binding: ViewListGalleryItemBinding): RecyclerView.ViewHolder(binding.root)
}