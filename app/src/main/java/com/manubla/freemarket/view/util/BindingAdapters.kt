package com.manubla.freemarket.view.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.manubla.freemarket.R
import com.manubla.freemarket.view.adapter.image.ImageAdapter
import com.manubla.freemarket.view.model.UserLevel

private const val ROUNDING_CORNERS = 20

@BindingAdapter("bindImageResource")
fun bindImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, url: String) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transform(CenterCrop(), RoundedCorners(ROUNDING_CORNERS))
        .placeholder(R.drawable.ic_placeholder)
    Glide.with(imageView.context)
        .load(url)
        .apply(requestOptions)
        .into(imageView)
}

@BindingAdapter("bindImages")
fun bindImages(recyclerView: RecyclerView, urls: List<String>?) {
    recyclerView.adapter?.let { adapter ->
        urls?.let {
            (adapter as? ImageAdapter)?.urls = it
        }
    }
}

@BindingAdapter("bindUserLevel")
fun bindUserLevel(textView: TextView, userLevel: UserLevel?) {
    val text = when(userLevel) {
        UserLevel.LEVEL_TYPE_1 -> textView.context.getString(R.string.txt_user_level_1)
        UserLevel.LEVEL_TYPE_2 -> textView.context.getString(R.string.txt_user_level_2)
        UserLevel.LEVEL_TYPE_3 -> textView.context.getString(R.string.txt_user_level_3)
        UserLevel.LEVEL_TYPE_4 -> textView.context.getString(R.string.txt_user_level_4)
        UserLevel.LEVEL_TYPE_5 -> textView.context.getString(R.string.txt_user_level_5)
        else -> textView.context.getString(R.string.txt_user_level_0)
    }
    textView.text = text
}