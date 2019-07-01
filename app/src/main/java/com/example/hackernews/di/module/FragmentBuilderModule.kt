package com.example.hackernews.di.module

import com.example.hackernews.ui.newslist.NewsListFragment
import com.example.hackernews.ui.newsdetails.NewsDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binding fragments
 * */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeNewsListFragment(): NewsListFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDetailsFragment(): NewsDetailsFragment
}