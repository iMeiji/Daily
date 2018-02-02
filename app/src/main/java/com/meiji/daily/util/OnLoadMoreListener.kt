package com.meiji.daily.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by Meiji on 2017/6/8.
 */

abstract class OnLoadMoreListener : RecyclerView.OnScrollListener() {

    private var layoutManager: LinearLayoutManager? = null
    private var itemCount: Int = 0
    private var lastPosition: Int = 0
    private var lastItemCount: Int = 0

    abstract fun onLoadMore()

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (recyclerView!!.layoutManager is LinearLayoutManager) {
            layoutManager = recyclerView.layoutManager as LinearLayoutManager

            itemCount = layoutManager!!.itemCount
            lastPosition = layoutManager!!.findLastCompletelyVisibleItemPosition()
        } else {
            Log.e("OnLoadMoreListener", "The OnLoadMoreListener only support LinearLayoutManager")
            return
        }

        if (lastItemCount != itemCount && lastPosition == itemCount - 1) {
            lastItemCount = itemCount
            this.onLoadMore()
        }
    }

    //    @Override
    //    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    //        super.onScrollStateChanged(recyclerView, newState);
    //        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
    //            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
    //                if (!recyclerView.canScrollVertically(1)) {
    //                    this.doAction();
    //                }
    //            }
    //        }
    //    }
}