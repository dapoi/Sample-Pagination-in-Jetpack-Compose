package com.dapascript.newscompose.di

import android.content.Context
import com.dapascript.newscompose.data.repository.NewsRepository
import com.dapascript.newscompose.data.repository.NewsRepositoryImpl
import com.dapascript.newscompose.data.source.service.ApiService
import com.dapascript.newscompose.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAuthInterceptor(
        @ApplicationContext context: Context
    ): AuthInterceptor = AuthInterceptor(context)

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = BODY }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)

    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient,
        retrofitBuilder: Retrofit.Builder,
    ): ApiService = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

    @Provides
    fun provideRepository(
        apiService: ApiService
    ): NewsRepository = NewsRepositoryImpl(apiService)
}