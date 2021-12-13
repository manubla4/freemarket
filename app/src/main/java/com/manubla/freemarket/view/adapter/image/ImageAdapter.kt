package com.manubla.freemarket.view.adapter.image

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.databinding.ViewImageGalleryBinding
import com.manubla.freemarket.view.viewholder.ImageViewHolder

@SuppressLint("NotifyDataSetChanged")
class ImageAdapter: RecyclerView.Adapter<ImageViewHolder>() {

    var urls = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(ViewImageGalleryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int = urls.size
}