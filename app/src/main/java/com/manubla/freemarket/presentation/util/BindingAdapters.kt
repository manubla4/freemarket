package com.manubla.freemarket.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.model.Restaurant
import com.manubla.freemarket.presentation.helper.visibleIf


@BindingAdapter("bindRestaurantDiscount")
fun bindRestaurantDiscount(textView: TextView, discount: Int) {
    val text = "$discount% descuento hoy"
    textView.text = text
    textView.visibleIf(discount > 0)
}

@BindingAdapter("bindRestaurantDistance")
fun bindRestaurantDistance(textView: TextView, distance: Double) {
    val text = "$distance km."
    textView.text = text
}

@BindingAdapter("bindRestaurantOnlinePayment")
fun bindRestaurantOnlinePayment(textView: TextView, onlinePayment: Boolean) {
    textView.visibleIf(onlinePayment)
}

@BindingAdapter("bindRestaurantTime")
fun bindRestaurantTime(textView: TextView, restaurant: Restaurant) {
    val text = "${restaurant.deliveryTimeMinMinutes} - ${restaurant.deliveryTimeMaxMinutes} min."
    textView.text = text
}

@BindingAdapter("bindRestaurantScore")
fun bindRestaurantScore(textView: TextView, score: Double) {
     textView.text = score.toString()
}

@BindingAdapter("bindRestaurantLogo")
fun bindRestaurantLogo(imageView: ImageView, path: String) {
    val url = BuildConfig.IMAGES_URL + path
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context).load(url).apply(requestOptions).into(imageView)
}