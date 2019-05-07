package com.ravn.edsonkrikey.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.di.ViewModelKey
import com.ravn.edsonkrikey.ui.detailsscreen.DetailsFragment
import com.ravn.edsonkrikey.ui.detailsscreen.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Module(includes = [
    DetailsViewModelModule.ProvideViewModel::class
])
abstract class DetailsViewModelModule {
    @ContributesAndroidInjector(modules = [
        InjectFragmentViewModel::class
    ])
    abstract fun contributesDetailsFragment(): DetailsFragment

    @Module
    class InjectFragmentViewModel {

        @Provides
        fun provideDetailsViewModel(
        factory: ViewModelProvider.Factory,
        target: DetailsFragment
        ) = ViewModelProviders.of(target, factory).get(DetailsViewModel::class.java)
    }

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(DetailsViewModel::class)
        fun provideDetailsViewModel(app: App): ViewModel = DetailsViewModel(app)
    }
}