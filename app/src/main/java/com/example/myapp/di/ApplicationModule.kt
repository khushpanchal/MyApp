package com.example.myapp.di

import androidx.room.Room
import com.example.myapp.common.dispatcher.DefaultDispatcherProvider
import com.example.myapp.common.dispatcher.DispatcherProvider
import com.example.myapp.common.logger.AppLogger
import com.example.myapp.common.logger.Logger
import com.example.myapp.common.networkhelper.NetworkHelper
import com.example.myapp.common.networkhelper.NetworkHelperImpl
import com.example.myapp.data.database.AppDatabaseService
import com.example.myapp.data.database.DatabaseService
import com.example.myapp.data.database.MainDatabase
import com.example.myapp.data.network.ApiInterface
import com.example.myapp.data.network.ApiKeyInterceptor
import com.example.myapp.data.network.FakeNetworkService
import com.example.myapp.data.repository.MainRepository
import com.example.myapp.ui.viewmodels.MainViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val API_KEY = "ApiKey"
private const val BASE_URL = "BaseUrl"
private const val DB_NAME = "DbName"

val appModule = module {
    single(named(API_KEY)) { "api key" }
    single(named(BASE_URL)) { "http://localhost/" } // Pass this if no other base url is required.
    single(named(DB_NAME)) { "article_db" }

    single { GsonConverterFactory.create() }
    single { ApiKeyInterceptor(get(named(API_KEY))) }

    single<ApiInterface> {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .build()

        Retrofit
            .Builder()
            .client(client) // Adding client to intercept all responses.
            .baseUrl(get<String>(named(BASE_URL)))
            .addConverterFactory(get())
            .build()
            .create(ApiInterface::class.java)
    }

    single { FakeNetworkService(androidContext()) }
    single<DispatcherProvider> { DefaultDispatcherProvider() }
    single<NetworkHelper> { NetworkHelperImpl(androidContext()) }
    single<Logger> { AppLogger() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MainDatabase::class.java,
            get<String>(named(DB_NAME))
        ).build()
    }
    single<DatabaseService> { AppDatabaseService(get()) }
    single { MainRepository(get(), get()) }

    viewModel { MainViewModel(get(), get(), get()) }
}