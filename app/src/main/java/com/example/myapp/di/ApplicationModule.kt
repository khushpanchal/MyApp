package com.example.myapp.di

import android.app.Application
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
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
import com.example.myapp.data.model.MainData
import com.example.myapp.data.network.ApiInterface
import com.example.myapp.data.network.ApiKeyInterceptor
import com.example.myapp.data.network.FakeNetworkService
import com.example.myapp.ui.paging.MainPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @ApiKey
    @Provides
    fun provideApiKey(): String = "api key"

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "http://localhost/" //Pass this if no other base url is required.

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonFactory: GsonConverterFactory,
        apiKeyInterceptor: ApiKeyInterceptor //if required
    ): ApiInterface {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor) //add if required
            .build()

        return Retrofit
            .Builder()
            .client(client) //adding client to intercept all responses
            .baseUrl(baseUrl)
            .addConverterFactory(gsonFactory)
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideFakeNetworkService(@ApplicationContext appContext: Context): FakeNetworkService {
        return FakeNetworkService(appContext)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()


    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return AppLogger()
    }

    @Provides
    @Singleton
    fun providePager(
        mainPagingSource: MainPagingSource
    ): Pager<Int, MainData> {
        return Pager(
            config = PagingConfig(
                pageSize = 15
            )
        ) {
            mainPagingSource
        }
    }

    @DbName
    @Provides
    fun provideDbName(): String = "article_db"

    @Singleton
    @Provides
    fun provideArticleDatabase(
        application: Application,
        @DbName dbName: String
    ): MainDatabase {
        return Room.databaseBuilder(
            application,
            MainDatabase::class.java,
            dbName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(mainDatabase: MainDatabase): DatabaseService {
        return AppDatabaseService(mainDatabase)
    }
}