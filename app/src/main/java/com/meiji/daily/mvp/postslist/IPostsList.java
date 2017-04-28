package com.meiji.daily.mvp.postslist;

import com.meiji.daily.bean.PostsListBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/24.
 */

interface IPostsList {

    interface View {

        /**
         * 请求数据
         */
        void onRequestData();

        /**
         * 设置适配器
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

    interface Presenter {

        /**
         * 请求数据
         */
        void doRequestData(String url, int offset);

        /**
         * 设置适配器
         */
        void doSetAdapter();

        /**
         * 获取点击栏信息
         */
        void doOnClickItem(int position);

        /**
         * 刷新 清空list
         */
        void doRefresh();

        /**
         * 请求数据失败
         */
        void onFail();

    }

    @Deprecated
    interface Model {

        /**
         * 请求数据
         */
        boolean retrofitRequest(String slug, int offset);

        /**
         * 返回数据
         */
        List<PostsListBean> getList();

        /**
         * 结束
         */
        void onDestroy();
    }
}
