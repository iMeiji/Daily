package com.meiji.daily.mvp.postscontent;

/**
 * Created by Meiji on 2016/11/24.
 */

public interface IPostsContent {

    interface View {

        void onSetWebView(String url);

        /**
         * 请求数据失败
         */
        void onFail();
    }

    interface Presenter {

        void doRequestData(int slug);

        void onFail();

        void onDestroy();
    }

    interface Model {

        boolean getRequestData(int slug);

        String getContent();

        void onDestroy();
    }
}
