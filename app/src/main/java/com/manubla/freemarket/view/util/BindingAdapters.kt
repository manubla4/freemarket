package com.manubla.freemarket.view.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.manubla.freemarket.view.adapter.image.ImageAdapter

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, url: String) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context).load(url).apply(requestOptions).into(imageView)
}

@BindingAdapter("bindImages")
fun bindImages(recyclerView: RecyclerView, urls: List<String>) {
    recyclerView.adapter?.let {
        (it as? ImageAdapter)?.urls = urls
    }
}