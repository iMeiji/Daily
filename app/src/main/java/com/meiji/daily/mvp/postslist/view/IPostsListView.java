package com.meiji.daily.mvp.postslist.view;

import com.meiji.daily.mvp.postslist.model.PostsListBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/18.
 */

public interface IPostsListView {
    /**
     * 请求数据
     */
    void onRequestData();

    /**
     * 设置适配器
     *
     * @param list
     */
    void onSetAdapter(List<PostsListBean> list);

    /**
     * 正在刷新
     */
    void onShowRefreshing();

    /**
     * 完成刷新
     */
    void onHideRefreshing();

    /**
     * 请求数据失败
     */
    void onFail();
}
