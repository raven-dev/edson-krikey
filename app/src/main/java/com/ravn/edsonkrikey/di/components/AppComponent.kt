package com.ravn.edsonkrikey.di.components

import android.app.Application
import android.content.Context
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.di.modules.AppModule
import com.ravn.edsonkrikey.di.modules.viewmodel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent {
    fun inject(application: Application)

    fun context(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}