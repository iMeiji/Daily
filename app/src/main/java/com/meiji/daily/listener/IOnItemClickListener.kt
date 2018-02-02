package com.meiji.daily.listener

import android.view.View

/**
 * Created by Meiji on 2016/11/18.
 */

interface IOnItemClickListener {

    /**
     * RecyclerView Item点击事件
     *
     * @param view
     * @param position
     */
    fun onClick(view: View, position: Int)
}
