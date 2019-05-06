package com.ravn.edsonkrikey.extensions

import android.content.Context
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.di.components.AppComponent

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

// Dependency Injection
fun Context.appComponent(): AppComponent {
    return (applicationContext as App).component
}

// Check is a fragment transaction is safe (Activity)
fun AppCompatActivity.safeFragmentTransaction(action: (AppCompatActivity) -> Unit) {
    if (isFinishing || isDestroyed) return
    try {
        action.invoke(this)
    } catch (e: IllegalStateException) {
        if (e.message?.contains("onSaveInstanceState") == true) return
        else throw(e)
    }
}

fun Fragment.safeFragmentTransaction(action: (AppCompatActivity) -> Unit) {
    val activity = activity as? AppCompatActivity ?: return
    activity.safeFragmentTransaction(action)
}

internal fun Fragment.onUi(action: (View) -> Unit) {
    val view = view ?: return
    if (Looper.myLooper() != Looper.getMainLooper()) {
        view.post { action.invoke(view) }
    } else {
        action.invoke(view)
    }
}

/** Ternary operator **/
infix fun <T> Boolean.then(param: T): T? = if (this) param else null