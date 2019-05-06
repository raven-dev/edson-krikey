package com.ravn.edsonkrikey.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.extensions.currentFragment
import com.ravn.edsonkrikey.extensions.safeFragmentTransaction

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(javaClass.getAnnotation(ActivityLayout::class.java).layout)
        super.onCreate(savedInstanceState)
    }

    fun launchScreen(fragment: Fragment,
                     stackAction: BackStack = BackStack.ADD) = safeFragmentTransaction {
        if (stackAction == BackStack.REPLACE) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        val tx = supportFragmentManager.beginTransaction()

        when (stackAction) {
            BackStack.ADD, BackStack.ADD_OVERLAY -> tx.addToBackStack(null)
        }

        when (stackAction) {
            BackStack.ADD_OVERLAY -> tx.add(R.id.appContainer, fragment)
            else -> tx.replace(R.id.appContainer, fragment)
        }
        tx.commitAllowingStateLoss()
    }

    fun rootScreen() = safeFragmentTransaction {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        if (currentFragment()?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    companion object {
        enum class BackStack {
            ADD,
            ADD_OVERLAY,
            REPLACE,
            NONE
        }
    }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityLayout(@LayoutRes val layout: Int)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentLayout(@LayoutRes val layout: Int)