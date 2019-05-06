package com.ravn.edsonkrikey.ui.mainscreen

import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.core.BaseViewModel

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class MainViewModel(val app: App): BaseViewModel(app) {

    override fun onStart() {
        super.onStart()
        app.component.inject(this)
    }
}