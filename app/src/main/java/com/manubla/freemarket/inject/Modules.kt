package com.manubla.freemarket.inject

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.adapter.ZonedDateTimeAdapter
import com.manubla.freemarket.data.dao.ProductDao
import com.manubla.freemarket.data.datastore.products.cloud.ProductsDataStoreCloud
import com.manubla.freemarket.data.datastore.products.cloud.ProductsDataStoreCloudImpl
import com.manubla.freemarket.data.datastore.products.database.ProductsDataStoreDatabase
import com.manubla.freemarket.data.datastore.products.database.ProductsDataStoreDatabaseImpl
import com.manubla.freemarket.data.repository.products.ProductsSourceRepository
import com.manubla.freemarket.data.repository.products.ProductsSourceRepositoryImpl
import com.manubla.freemarket.data.service.ProductService
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

    factory<ProductService> {
        get<Retrofit>().create(ProductService::class.java)
    }
}


val productsModule = module {
    factory<ProductsSourceRepository> {
        ProductsSourceRepositoryImpl(
            get<ProductsDataStoreDatabase>(),
            get<ProductsDataStoreCloud>()
        )
    }
    factory<ProductsDataStoreCloud> {
        ProductsDataStoreCloudImpl(
            get<ProductService>()
        )
    }
    factory<ProductsDataStoreDatabase> {
        ProductsDataStoreDatabaseImpl(
            get<ProductDao>()
        )
    }
}

val viewModelsModule = module {
//    viewModel { SplashViewModel(get(), get()) }
//    viewModel { HomeViewModel(get(), get(), get()) }
//    viewModel { MapViewModel(get(), get()) }
}