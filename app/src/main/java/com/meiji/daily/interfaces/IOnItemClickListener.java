package com.meiji.daily.interfaces;

import android.view.View;

/**
 * Created by Meiji on 2016/11/18.
 */

public interface IOnItemClickListener {

    /**
     * RecyclerView Item点击事件
     *
     * @param view
     * @param position
     */
    void onClick(View view, int position);
}
