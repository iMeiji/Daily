package com.meiji.daily.mvp.postslist.presenter;

/**
 * Created by Meiji on 2016/11/18.
 */

public interface IPostsListPresenter {

    /**
     * 请求数据
     */

    void doRequestData(String url);

    /**
     * 设置适配器
     */
    void doSetAdapter();

    /**
     * 获取点击栏信息
     *
     * @param position
     */
    void doOnClickItem(int position);

    void doRefresh();

    void onDestroy();

    void onFail();

}
