package com.manubla.freemarket.inject

import android.content.Context
import com.manubla.freemarket.data.repository.products.ProductsSourceRepository
import com.manubla.freemarket.data.repository.products.ProductsSourceRepositoryImpl
import com.manubla.freemarket.data.source.network.builder.initRetrofit
import com.manubla.freemarket.data.source.network.datastore.products.ProductsDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.products.ProductsDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.CurrencyService
import com.manubla.freemarket.data.source.network.service.ProductService
import com.manubla.freemarket.data.source.network.service.StateService
import com.manubla.freemarket.data.source.network.service.UserService
import com.manubla.freemarket.data.source.storage.AppDatabase
import com.manubla.freemarket.data.source.storage.builder.initRoomDatabase
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao
import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.dao.StateDao
import com.manubla.freemarket.data.source.storage.dao.UserDao
import com.manubla.freemarket.data.source.storage.datastore.products.ProductsDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.products.ProductsDataStoreDatabaseImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val storageModule = module {
    single<AppDatabase> {
        initRoomDatabase(get<Context>())
    }
    factory<ProductDao> { get<AppDatabase>().productsDao() }
    factory<UserDao> { get<AppDatabase>().usersDao() }
    factory<StateDao> { get<AppDatabase>().statesDao() }
    factory<CurrencyDao> { get<AppDatabase>().currenciesDao() }
}

val networkModule = module {
    single<Retrofit> {
        initRetrofit(get<Context>())
    }
    factory<ProductService> {
        get<Retrofit>().create(ProductService::class.java)
    }
    factory<CurrencyService> {
        get<Retrofit>().create(CurrencyService::class.java)
    }
    factory<StateService> {
        get<Retrofit>().create(StateService::class.java)
    }
    factory<UserService> {
        get<Retrofit>().create(UserService::class.java)
    }
}

val productsModule = module {
    factory<ProductsDataStoreNetwork> {
        ProductsDataStoreNetworkImpl(
            get<ProductService>()
        )
    }
    factory<ProductsDataStoreDatabase> {
        ProductsDataStoreDatabaseImpl(
            get<ProductDao>()
        )
    }
    factory<ProductsSourceRepository> {
        ProductsSourceRepositoryImpl(
            get<ProductsDataStoreDatabase>(),
            get<ProductsDataStoreNetwork>()
        )
    }
}

val viewModelsModule = module {
//    viewModel { SplashViewModel(get(), get()) }
//    viewModel { HomeViewModel(get(), get(), get()) }
//    viewModel { MapViewModel(get(), get()) }
}