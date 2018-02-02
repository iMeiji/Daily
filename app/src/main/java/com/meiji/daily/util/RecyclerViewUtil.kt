package com.meiji.daily.util

import android.support.v7.widget.RecyclerView

/**
 * Created by Meiji on 2017/7/13.
 */

object RecyclerViewUtil {

    /**
     * 使RecyclerView缓存中在pool中的Item失效
     */
    fun invalidateCacheItem(recyclerView: RecyclerView) {
        val recyclerViewClass = RecyclerView::class.java
        try {
            val declaredField = recyclerViewClass.getDeclaredField("mRecycler")
            declaredField.isAccessible = true
            val declaredMethod = Class.forName(RecyclerView.Recycler::class.java.name)
                    .getDeclaredMethod("clear", *arrayOfNulls<Class<*>>(0) as Array<Class<*>>)
            declaredMethod.isAccessible = true
            declaredMethod.invoke(declaredField.get(recyclerView))
            val recycledViewPool = recyclerView.recycledViewPool
            recycledViewPool.clear()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
