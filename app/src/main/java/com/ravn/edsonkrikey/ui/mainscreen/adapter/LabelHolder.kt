package com.ravn.edsonkrikey.ui.mainscreen.adapter

import android.view.View
import com.ravn.edsonkrikey.core.BaseViewHolder
import com.ravn.edsonkrikey.repository.remote.Label
import kotlinx.android.synthetic.main.view_item_label.view.*

/**
 * Created by Edson Arratea Ope on 5/7/19.
 * Ravn Development
 **/

class LabelHolder(itemView: View) : BaseViewHolder<Label>(itemView) {
    override fun updateView(item: Any) {
        val label = item as Label
        itemView.kindLabel.text = label.kind
    }

}