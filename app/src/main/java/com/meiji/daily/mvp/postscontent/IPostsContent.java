package com.meiji.daily.mvp.postscontent;


import com.meiji.daily.mvp.base.IBaseView;

/**
 * Created by Meiji on 2016/11/24.
 */

interface IPostsContent {

    interface View extends IBaseView {

        /**
         * 加载网页
         */
        void onSetWebView(String url);
    }

    interface Presenter {

        /**
         * 请求数据
         */
        void doRequestData(int slug);
    }
}
