package com.example.hackernews.di.component

import com.example.hackernews.HackerNewsApp
import com.example.hackernews.di.module.ActivityBuilderModule
import com.example.hackernews.di.module.ReposirotyModule
import com.example.hackernews.di.module.ViewModelModule
import com.example.hackernews.domain.ApiInterface
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ActivityBuilderModule::class, ReposirotyModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(@Named("app")app: HackerNewsApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: HackerNewsApp)
}