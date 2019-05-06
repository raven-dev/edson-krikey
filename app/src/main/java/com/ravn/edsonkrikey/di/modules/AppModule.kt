package com.ravn.edsonkrikey.di.modules

import android.content.Context
import com.ravn.edsonkrikey.core.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(application: App): Context {
        return application.applicationContext
    }
}