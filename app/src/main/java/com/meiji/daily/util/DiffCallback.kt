package com.meiji.daily.util

import android.support.v7.util.DiffUtil

import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 * Created by Meiji on 2018/1/25.
 */

class DiffCallback private constructor(private val oldItems: Items?, private val newItems: Items?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newItems?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems!![oldItemPosition] == newItems!![newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems!![oldItemPosition].hashCode() == newItems!![newItemPosition].hashCode()
    }

    companion object {

        fun create(oldItems: Items, newItems: Items, adapter: MultiTypeAdapter) {
            val diffCallback = DiffCallback(oldItems, newItems)
            val result = DiffUtil.calculateDiff(diffCallback, true)
            result.dispatchUpdatesTo(adapter)
        }
    }
}
