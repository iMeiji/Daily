package com.meiji.daily.mvp.postscontent;

/**
 * Created by Meiji on 2016/11/24.
 */

interface IPostsContent {

    interface View {

        /**
         * 加载网页
         */
        void onSetWebView(String url);

        /**
         * 请求数据失败
         */
        void onFail();
    }

    interface Presenter {

        /**
         * 请求数据
         */
        void doRequestData(int slug);

        /**
         * 设置浏览器
         */
        void doSetWebView();

        /**
         * 请求数据失败
         */
        void onFail();

        /**
         * 结束
         */
        void onDestroy();
    }

    interface Model {

        /**
         * 请求数据
         */
        boolean retrofitRequest(int slug);

        /**
         * 返回内容
         */
        String getContent();

        /**
         * 结束
         */
        void onDestroy();
    }
}
