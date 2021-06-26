package com.example.nasadatasetdemo.view.gallery

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasadatasetdemo.databinding.ViewListGalleryItemBinding
import com.example.nasadatasetdemo.view.pojo.NasaItemPojo

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private val listNasaData by lazy { ArrayList<NasaItemPojo>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ViewListGalleryItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = listNasaData[position].title

        if(listNasaData[position].thumbnailBitmap != null) {
            holder.binding.image.setImageBitmap(listNasaData[position].thumbnailBitmap)
        } else {
            holder.binding.image.setImageBitmap(null)
        }
    }

    override fun getItemCount() = listNasaData.size

    fun setListNasaData(list: ArrayList<NasaItemPojo>) {
        listNasaData.clear()
        listNasaData.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemBitmap(position: Int, bitmap: Bitmap) {
        listNasaData[position].thumbnailBitmap = bitmap
        notifyItemChanged(position)
    }

    inner class ViewHolder(val binding: ViewListGalleryItemBinding): RecyclerView.ViewHolder(binding.root)
}