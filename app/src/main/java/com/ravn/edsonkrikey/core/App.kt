package com.ravn.edsonkrikey.core

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.ravn.edsonkrikey.BuildConfig
import com.ravn.edsonkrikey.di.components.AppComponent
import com.ravn.edsonkrikey.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class App: Application(), HasActivityInjector, HasSupportFragmentInjector {

    val component: AppComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    val version: String
        get() {
            return BuildConfig.VERSION_NAME
        }

    override fun onCreate() {
        App.context = this
        super.onCreate()
        component.inject(context)
    }

    // Activity support
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    // Fragment support
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    companion object {
        lateinit var context: App
    }
}