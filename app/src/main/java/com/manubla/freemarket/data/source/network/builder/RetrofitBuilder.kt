package com.manubla.freemarket.data.source.network.builder

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.source.network.adapter.ZonedDateTimeAdapter
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val CONTENT_TYPE = "Content-Type"
private const val APPLICATION_JSON = "application/json"

fun initRetrofit(context: Context): Retrofit {

    val converter = GsonConverterFactory.create(
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
            .create()
    )

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(context))
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()
            chain.proceed(request)
        }.build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converter)
        .client(okHttpClient)
        .build()

}