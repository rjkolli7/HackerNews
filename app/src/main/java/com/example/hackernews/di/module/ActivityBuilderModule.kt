package com.example.hackernews.di.module

import com.example.hackernews.testing.OpenForTesting
import com.example.hackernews.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Bind activities and fragment activities with fragments.
 * */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}