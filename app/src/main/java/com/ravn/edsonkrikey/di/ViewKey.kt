package com.ravn.edsonkrikey.di

import android.view.View
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewKey(val value: KClass<out View>)