package com.manubla.freemarket.inject

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.freemarket.data.adapter.ZonedDateTimeAdapter
import com.manubla.freemarket.data.repository.restaurants.RestaurantsDataStoreFactory
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepositoryImpl
import com.manubla.freemarket.data.source.AppDatabase
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.threeten.bp.ZonedDateTime
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val storageModule = module {
    single {
        Room.databaseBuilder<AppDatabase>(
            get<Context>().applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
        .build()
    }
    single { get<AppDatabase>().productsDao() }
}


val networkModule = module {
    single<Converter.Factory> {
        GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                .create()
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(get()))
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(get())
        .client(get())
        .build()
    }

    single<RestaurantService> {
        get<Retrofit>().create(RestaurantService::class.java)
    }
}


val restaurantsModule = module {
    single { RestaurantsDataStoreFactory(get(), get(), get()) }
    single<RestaurantsSourceRepository> {
        RestaurantsSourceRepositoryImpl(get())
    }
}

val viewModelsModule = module {
//    viewModel { SplashViewModel(get(), get()) }
//    viewModel { HomeViewModel(get(), get(), get()) }
//    viewModel { MapViewModel(get(), get()) }
}