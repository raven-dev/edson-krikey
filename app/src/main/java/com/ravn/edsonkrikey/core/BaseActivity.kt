package com.ravn.edsonkrikey.core

import android.os.Bundle
import android.transition.Explode
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
                     stackAction: BackStack = BackStack.ADD,
                     animation: AnimationStyle = AnimationStyle.SLIDE) = safeFragmentTransaction {
        if (stackAction == BackStack.REPLACE) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        var animationToUse = animation

        val tx = supportFragmentManager.beginTransaction()
        if(!supportFragmentManager.fragments.isEmpty()) {
            when (animationToUse) {
                AnimationStyle.SLIDE -> {
                    tx.setCustomAnimations(
                        R.anim.screen_enter, R.anim.screen_exit,
                        R.anim.screen_pop_enter, R.anim.screen_pop_exit
                    )
                }
                AnimationStyle.MODAL -> {
                    tx.setCustomAnimations(
                        R.anim.modal_screen_enter, R.anim.modal_screen_exit,
                        R.anim.modal_screen_pop_enter, R.anim.modal_screen_pop_exit
                    )
                }
                else -> {
                    tx.setCustomAnimations(
                        R.anim.overlay_screen_enter, R.anim.overlay_screen_exit,
                        R.anim.overlay_screen_enter, R.anim.overlay_screen_exit
                    )
                }
            }
        }
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

        enum class AnimationStyle {
            NONE,
            SLIDE,
            MODAL
        }
    }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityLayout(@LayoutRes val layout: Int)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentLayout(@LayoutRes val layout: Int)