package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.databinding.ViewImageGalleryBinding
import com.manubla.freemarket.view.util.bindImage

class ImageViewHolder(
    private val viewBinding: ViewImageGalleryBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(url: String) {
        bindImage(viewBinding.imageView, url)
    }

    companion object {
        fun from(parent: ViewGroup): ImageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = ViewImageGalleryBinding.inflate(inflater, parent, false)
            return ImageViewHolder(viewBinding)
        }
    }
}