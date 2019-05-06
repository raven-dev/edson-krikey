package com.ravn.edsonkrikey.di.components

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.di.modules.AppModule
import com.ravn.edsonkrikey.di.modules.NetworkModule
import com.ravn.edsonkrikey.di.modules.viewmodel.MainViewModelModule
import com.ravn.edsonkrikey.di.modules.viewmodel.ViewModelFactoryModule
import com.ravn.edsonkrikey.repository.ItunesRepository
import com.ravn.edsonkrikey.ui.mainscreen.MainViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ViewModelFactoryModule::class,
    MainViewModelModule::class
])
interface AppComponent {
    fun inject(application: Application)

    fun inject(viewModel: MainViewModel)

    fun okHttp(): OkHttpClient
    fun retrofitAPi(): Retrofit
    fun context(): Context
    fun gson(): Gson
    fun itunesRepo(): ItunesRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}