package com.example.hackernews.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.presentation.viewmodel.newsdetails.NewsDetailsViewModel
import com.example.hackernews.presentation.viewmodel.newslist.NewsListViewModel
import com.example.hackernews.testing.OpenForTesting
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@OpenForTesting
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    abstract fun bindNewsListViewModel(viewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    abstract fun bindNewsDetailsViewModel(viewModel: NewsDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}