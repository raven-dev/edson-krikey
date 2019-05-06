package com.ravn.edsonkrikey.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ravn.edsonkrikey.core.BaseActivity
import com.ravn.edsonkrikey.core.BaseFragment

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

fun Context.launchScreen(fragment: Fragment, stackAction: BaseActivity.Companion.BackStack = BaseActivity.Companion.BackStack.ADD) {
    (this as BaseActivity).launchScreen(fragment, stackAction)
}

fun Fragment.launchScreen(fragment: Fragment, stackAction: BaseActivity.Companion.BackStack = BaseActivity.Companion.BackStack.ADD) {
    activity?.launchScreen(fragment, stackAction)
}

fun Context.rootScreen() {
    (this as BaseActivity).rootScreen()
}

fun Fragment.rootScreen() {
    activity?.rootScreen()
}

fun Context.currentFragment(): BaseFragment<*, *>? {
    (this as BaseActivity).supportFragmentManager.fragments?.apply {
        for (i in size -1 downTo 0) {
            val fragment = get(i) as? BaseFragment<*, *> ?: continue
            if (fragment.isVisible)
                return fragment
        }
    }
    return null
}

fun Fragment.currentFragment(): Fragment? {
    return activity?.currentFragment()
}

fun Context.backStackSize() = (this as BaseActivity).supportFragmentManager.backStackEntryCount

fun Fragment.backStackSize() = activity?.backStackSize() ?: 0

fun Context.pressBackButton() {
    (this as? Activity)?.onBackPressed()
}

fun Fragment.pressBackButton() {
    activity?.pressBackButton()
}

fun Activity.closeKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}