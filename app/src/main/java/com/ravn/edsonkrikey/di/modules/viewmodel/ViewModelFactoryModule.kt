package com.ravn.edsonkrikey.di.modules.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.ravn.edsonkrikey.di.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}