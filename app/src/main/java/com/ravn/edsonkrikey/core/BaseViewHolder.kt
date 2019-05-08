package com.ravn.edsonkrikey.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Edson Arratea Ope on 5/7/19.
 * Ravn Development
 **/

abstract class BaseViewHolder<T : Any>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun updateView(item: Any)
}