package com.ravn.edsonkrikey.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.di.ViewModelKey
import com.ravn.edsonkrikey.ui.mainscreen.MainFragment
import com.ravn.edsonkrikey.ui.mainscreen.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Module(includes = [
        MainViewModelModule.ProvideViewModel::class
])
abstract class MainViewModelModule {
    @ContributesAndroidInjector(modules = [
        InjectFragmentViewModel::class
    ])
    abstract fun contributesMainFragment(): MainFragment

    @Module
    class InjectFragmentViewModel {

        @Provides
        fun provideMainViewModel(
        factory: ViewModelProvider.Factory,
        target: MainFragment
        ) = ViewModelProviders.of(target, factory).get(MainViewModel::class.java)
    }

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun provideMainViewModel(app: App): ViewModel = MainViewModel(app)
    }
}