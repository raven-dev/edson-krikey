package com.ravn.edsonkrikey.ui.detailsscreen

import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.core.BaseViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class DetailsViewModel(val app: App): BaseViewModel(app) {

    override fun onStart() {
        super.onStart()
        app.component.inject(this)
    }

    fun formatDate(date: Date?): CharSequence? {
        val FORMAT = SimpleDateFormat("MMMM, yyyy", Locale.US)
        return when (date) {
            null -> { "N/A" }
            else -> {
                try { FORMAT.format(date) }
                catch (e: ParseException) { "N/A" }
            }
        }
    }

    fun millisecondToMinutes(timeTrack: Long): String {
        return "${TimeUnit.MILLISECONDS.toMinutes(timeTrack)} min"
    }
}