package com.manubla.freemarket.inject

import android.content.Context
import android.location.LocationManager
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.freemarket.App
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.helper.adapter.ZonedDateTimeAdapter
import com.manubla.freemarket.data.helper.networking.NetworkingManager
import com.manubla.freemarket.data.repository.restaurants.RestaurantsDataStoreFactory
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepositoryImpl
import com.manubla.freemarket.data.repository.token.TokenDataStoreCloud
import com.manubla.freemarket.data.repository.token.TokenDataStoreStorage
import com.manubla.freemarket.data.service.LocationService
import com.manubla.freemarket.data.service.RestaurantService
import com.manubla.freemarket.data.service.TokenService
import com.manubla.freemarket.data.source.AppDatabase
import com.manubla.freemarket.presentation.view.home.HomeViewModel
import com.manubla.freemarket.presentation.view.map.MapViewModel
import com.manubla.freemarket.presentation.view.splash.SplashViewModel
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.threeten.bp.ZonedDateTime
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


val storageModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
    single {
        Room.databaseBuilder<AppDatabase>(
            get<Context>().applicationContext,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().restaurantDao() }
    single { TokenDataStoreStorage(get()) }
}


val networkModule = module {
    single { NetworkingManager(get()) }
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
                    .addHeader("Content-Type",
                        "application/json")
                    .addHeader("Authorization",
                        get<TokenDataStoreStorage>().getToken().access_token)
                    .build()
                chain.proceed(request)
            }.addInterceptor { chain ->
                val response = chain.proceed(chain.request())

                if (response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    App.reloadSplash()

                response
            }
            .build()
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

    single<TokenService> {
        get<Retrofit>().create(TokenService::class.java)
    }

    single { TokenDataStoreCloud(get(), get()) }
}


val locationModule = module {
    single { LocationService(get<Context>().getSystemService(Context.LOCATION_SERVICE) as LocationManager) }
}


val restaurantsModule = module {
    single { RestaurantsDataStoreFactory(get(), get(), get()) }
    single<RestaurantsSourceRepository> {
        RestaurantsSourceRepositoryImpl(get())
    }
}

val viewModelsModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MapViewModel(get(), get()) }
}