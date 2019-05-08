package com.ravn.edsonkrikey.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.ravn.edsonkrikey.extensions.ClickListener
import com.ravn.edsonkrikey.extensions.ItemType
import java.lang.RuntimeException
import kotlin.reflect.KClass

/**
 * Created by Edson Arratea Ope on 4/24/19.
 * Ravn Development
 **/

abstract class BaseAdapter<T : Any>(val listener: ClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {
    internal val items: MutableList<T> = mutableListOf()
    internal val itemsType: MutableMap<KClass<*>, ItemType> = mutableMapOf()

    override fun getItemCount(): Int = items.size
    open fun getItem(position: Int): T = items[position]

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return getViewHolder(view, viewType)
    }

    @CallSuper
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.updateView(items[position])
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val itemType = itemsType[item::class]
        if (itemType != null)
            return itemType.itemLayoutId
        throw RuntimeException("Could not found the specified class")
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun addItem(item: T) {
        addItems(listOf(item))
    }

    @CallSuper
    open fun addItems(items: Collection<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItems() {
        items.clear()
    }

    abstract fun getViewHolder(view: View, viewType: Int): BaseViewHolder<*>
}

/**
 * Establish a match between the data class and the layout file
 */
fun <T : Any, Adapter : BaseAdapter<T>> Adapter.match(kClass: KClass<*>, itemLayoutId: Int): Adapter {
    itemsType[kClass] = ItemType(itemLayoutId)
    return this
}