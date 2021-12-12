package com.manubla.freemarket.inject.module

import android.content.Context
import com.manubla.freemarket.data.repository.currency.CurrencySourceRepository
import com.manubla.freemarket.data.repository.currency.CurrencySourceRepositoryImpl
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.repository.product.ProductSourceRepositoryImpl
import com.manubla.freemarket.data.repository.state.StateSourceRepository
import com.manubla.freemarket.data.repository.state.StateSourceRepositoryImpl
import com.manubla.freemarket.data.repository.user.UserSourceRepository
import com.manubla.freemarket.data.repository.user.UserSourceRepositoryImpl
import com.manubla.freemarket.data.source.network.builder.initRetrofit
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.CurrencyService
import com.manubla.freemarket.data.source.network.service.ProductService
import com.manubla.freemarket.data.source.network.service.StateService
import com.manubla.freemarket.data.source.network.service.UserService
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.data.source.storage.builder.initRoomDatabase
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao
import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.dao.StateDao
import com.manubla.freemarket.data.source.storage.dao.UserDao
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabaseImpl
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabaseImpl
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabaseImpl
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabaseImpl
import com.manubla.freemarket.data.source.storage.manager.DatabaseManager
import com.manubla.freemarket.data.source.storage.manager.DatabaseManagerImpl
import com.manubla.freemarket.view.alias.DiffUtil
import com.manubla.freemarket.view.alias.PagingAdapter
import com.manubla.freemarket.view.alias.ViewHolderProvider
import com.manubla.freemarket.view.fragment.DetailFragment
import com.manubla.freemarket.view.fragment.HomeFragment
import com.manubla.freemarket.view.fragment.SplashFragment
import com.manubla.freemarket.view.viewmodel.DetailViewModel
import com.manubla.freemarket.view.viewmodel.HomeViewModel
import com.manubla.freemarket.view.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val storageModule = module {
    single<AppDatabase> {
        initRoomDatabase(get<Context>())
    }
    factory<ProductDao> { get<AppDatabase>().productDao() }
    factory<UserDao> { get<AppDatabase>().userDao() }
    factory<StateDao> { get<AppDatabase>().stateDao() }
    factory<CurrencyDao> { get<AppDatabase>().currencyDao() }

    factory<ProductDataStoreDatabase> {
        ProductDataStoreDatabaseImpl(
            get<ProductDao>()
        )
    }
    factory<UserDataStoreDatabase> {
        UserDataStoreDatabaseImpl(
            get<UserDao>()
        )
    }
    factory<CurrencyDataStoreDatabase> {
        CurrencyDataStoreDatabaseImpl(
            get<CurrencyDao>()
        )
    }
    factory<StateDataStoreDatabase> {
        StateDataStoreDatabaseImpl(
            get<StateDao>()
        )
    }
    factory<DatabaseManager> {
        DatabaseManagerImpl(
            get<ProductDataStoreDatabase>(),
            get<UserDataStoreDatabase>(),
            get<StateDataStoreDatabase>()
        )
    }
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
    factory<ProductDataStoreNetwork> {
        ProductDataStoreNetworkImpl(
            get<ProductService>()
        )
    }
    factory<UserDataStoreNetwork> {
        UserDataStoreNetworkImpl(
            get<UserService>()
        )
    }
    factory<CurrencyDataStoreNetwork> {
        CurrencyDataStoreNetworkImpl(
            get<CurrencyService>()
        )
    }
    factory<StateDataStoreNetwork> {
        StateDataStoreNetworkImpl(
            get<StateService>()
        )
    }
}

val repositoriesModule = module {
    factory<ProductSourceRepository> {
        ProductSourceRepositoryImpl(
            get<DatabaseManager>(),
            get<ProductDataStoreDatabase>(),
            get<ProductDataStoreNetwork>()
        )
    }
    factory<UserSourceRepository> {
        UserSourceRepositoryImpl(
            get<UserDataStoreDatabase>(),
            get<UserDataStoreNetwork>()
        )
    }
    factory<CurrencySourceRepository> {
        CurrencySourceRepositoryImpl(
            get<CurrencyDataStoreDatabase>(),
            get<CurrencyDataStoreNetwork>()
        )
    }
    factory<StateSourceRepository> {
        StateSourceRepositoryImpl(
            get<StateDataStoreDatabase>(),
            get<StateDataStoreNetwork>()
        )
    }
}

val adaptersModule = module {
    scope<HomeFragment> {
        factory<DiffUtil> {
            DiffUtil()
        }
        factory<ViewHolderProvider> {
            ViewHolderProvider()
        }
        factory {
            PagingAdapter(
                get<DiffUtil>(),
                get<ViewHolderProvider>()
            )
        }
    }
}

val viewModelsModule = module {
    scope<SplashFragment> {
        viewModel {
            SplashViewModel(
                get<CurrencySourceRepository>()
            )
        }
    }
    scope<HomeFragment> {
        viewModel {
            HomeViewModel(
                get<ProductSourceRepository>()
            )
        }
    }
    scope<DetailFragment> {
        viewModel {
            DetailViewModel(
                get<ProductSourceRepository>(),
                get<UserSourceRepository>(),
                get<StateSourceRepository>()
            )
        }
    }
}