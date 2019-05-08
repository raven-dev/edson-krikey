package com.ravn.edsonkrikey.extensions

/**
 * Created by Edson Arratea Ope on 5/7/19.
 * Ravn Development
 **/

class ItemType(val itemLayoutId: Int, val variableId: Int) {
    constructor(itemLayoutId: Int): this(itemLayoutId, 0)
}


/**
 * Alias to add RecyclerView listeners
 */
typealias ClickListener = (Any, Any) -> Unit