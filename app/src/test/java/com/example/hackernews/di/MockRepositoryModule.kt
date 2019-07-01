package com.example.hackernews.di

import com.example.hackernews.di.module.ReposirotyModule

class MockRepositoryModule : ReposirotyModule() {
    fun getNewsListRepository() = providesNewsRepositoryImp(providesApiInterface(provideRetrofit(providesOkHttpClient())))

    fun getNewsDetailsRepository() = providesNewsDetailsRepositoryImp(providesApiInterface(provideRetrofit(providesOkHttpClient())))
}