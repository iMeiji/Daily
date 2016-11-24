package com.meiji.daily.mvp.postscontent.view;

/**
 * Created by Meiji on 2016/11/22.
 */

public interface IPostsContentView {

    void onSetWebView(String url);

    /**
     * 请求数据失败
     */
    void onFail();
}
