package com.ravn.edsonkrikey

import android.os.Bundle
import com.ravn.edsonkrikey.core.ActivityLayout
import com.ravn.edsonkrikey.core.BaseActivity
import com.ravn.edsonkrikey.ui.mainscreen.MainFragment

@ActivityLayout(R.layout.activity_main)
class MainActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchScreen(MainFragment.newInstance())
    }
}
