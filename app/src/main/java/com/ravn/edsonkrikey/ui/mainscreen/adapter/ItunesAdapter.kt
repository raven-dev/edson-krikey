package com.ravn.edsonkrikey.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ravn.edsonkrikey.R
import com.ravn.edsonkrikey.repository.remote.ItunesItems

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

open class ItunesAdapter(val listener: (ItunesItems?) -> Unit) : RecyclerView.Adapter<ItunesHolder>() {
    private val items = arrayListOf<ItunesItems>()

    fun addItems(items: List<ItunesItems>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged() // Hide to test
    }

    fun addSingleItem(item: ItunesItems) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItunesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_itunes, parent, false)
        return ItunesHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ItunesHolder, position: Int) {
        holder.updateView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}