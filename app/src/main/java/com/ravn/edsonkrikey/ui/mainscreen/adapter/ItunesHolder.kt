package com.ravn.edsonkrikey.ui.mainscreen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ravn.edsonkrikey.repository.remote.ItunesItems
import kotlinx.android.synthetic.main.view_item_itunes.view.*

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

class ItunesHolder(itemView: View, val listener: (ItunesItems?) -> Unit) : RecyclerView.ViewHolder(itemView) {

    fun updateView(item: ItunesItems) {
        itemView.setOnClickListener {
            listener.invoke(item)
        }
        itemView.apply {
            kindOfItem.text = item.kind
            Glide.with(context)
                .load(item.artworkUrl100)
                .into(imageItem)
            artistName.text = item.artistName
            trackName.text = item.trackName
        }
    }

}