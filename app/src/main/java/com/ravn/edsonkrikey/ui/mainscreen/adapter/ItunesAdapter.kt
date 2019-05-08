package com.ravn.edsonkrikey.ui.mainscreen.adapter

import android.view.View
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.core.BaseAdapter
import com.ravn.edsonkrikey.core.BaseViewHolder
import com.ravn.edsonkrikey.extensions.ClickListener


/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class ItunesAdapter(listener: ClickListener) : BaseAdapter<Any>(listener) {
    override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<*> {
        return when(viewType) {
            R.layout.view_item_itunes -> ItunesHolder(view, listener)
            else -> LabelHolder(view)
        }
    }

}