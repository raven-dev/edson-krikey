package com.ravn.edsonkrikey.ui.mainscreen.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ravn.edsonkrikey.core.BaseViewHolder
import com.ravn.edsonkrikey.extensions.ClickListener
import com.ravn.edsonkrikey.repository.remote.ItunesItems
import kotlinx.android.synthetic.main.view_item_itunes.view.*

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

class ItunesHolder(itemView: View, val listener: ClickListener) : BaseViewHolder<ItunesItems>(itemView) {
    override fun updateView(item: Any) {
        val itunesItem = item as ItunesItems
        itemView.setOnClickListener {
            listener.invoke(itunesItem)
        }
        itemView.apply {
            itemName.text = itunesItem.trackName
            category.text = itunesItem.primaryGenreName
            val priceString = "$${itunesItem.trackPrice}"
            price.text = priceString
            Glide.with(context)
                .load(itunesItem.artworkUrl100)
                .apply(RequestOptions().centerCrop())
                .into(artistImg)
        }
    }

}