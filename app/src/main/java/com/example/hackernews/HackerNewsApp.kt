package com.example.hackernews

import android.app.Activity
import android.app.Application
import com.example.hackernews.di.injector.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class HackerNewsApp : Application() , HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}