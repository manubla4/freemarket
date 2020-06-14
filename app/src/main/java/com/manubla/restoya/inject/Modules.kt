package com.manubla.restoya.inject

import android.content.Context
import android.location.LocationManager
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.restoya.BuildConfig
import com.manubla.restoya.data.helper.adapter.ZonedDateTimeAdapter
import com.manubla.restoya.data.helper.networking.NetworkingManager
import com.manubla.restoya.data.repository.restaurants.RestaurantsDataStoreFactory
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepositoryImpl
import com.manubla.restoya.data.repository.token.TokenDataStoreCloud
import com.manubla.restoya.data.repository.token.TokenDataStoreStorage
import com.manubla.restoya.data.service.LocationService
import com.manubla.restoya.data.service.RestaurantService
import com.manubla.restoya.data.service.TokenService
import com.manubla.restoya.data.source.AppDatabase
import com.manubla.restoya.presentation.view.home.HomeViewModel
import com.manubla.restoya.presentation.view.splash.SplashViewModel
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.threeten.bp.ZonedDateTime
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var storageModule = module {
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


var networkModule = module {
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

    single { TokenDataStoreCloud(get()) }
}


var locationModule = module {
    single { LocationService(get<Context>().getSystemService(Context.LOCATION_SERVICE) as LocationManager) }
}


var restaurantsModule = module {
    single { RestaurantsDataStoreFactory(get(), get(), get()) }
    single<RestaurantsSourceRepository> {
        RestaurantsSourceRepositoryImpl(get())
    }
}

var viewModelsModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
}