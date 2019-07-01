package com.example.hackernews.di.module

import com.example.hackernews.BuildConfig
import com.example.hackernews.domain.ApiInterface
import com.example.hackernews.domain.news.NewsRepositoryImp
import com.example.hackernews.domain.newsdetails.NewsDetailsRepositoryImp
import com.example.hackernews.testing.OpenForTesting
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@OpenForTesting
open class ReposirotyModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
        ApiInterface::class.java
    )

    @Provides
    @Singleton
    fun providesNewsRepositoryImp(apiInterface: ApiInterface): NewsRepositoryImp = NewsRepositoryImp(apiInterface)

    @Provides
    @Singleton
    fun providesNewsDetailsRepositoryImp(apiInterface: ApiInterface): NewsDetailsRepositoryImp = NewsDetailsRepositoryImp(apiInterface)
}