package com.meiji.daily.mvp.postslist;

import java.util.List;

/**
 * Created by Meiji on 2016/11/24.
 */

public interface IPostsList {

    interface View {
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

    interface Presenter {

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

    interface Model {

        boolean getRequestData(String url);

        List<PostsListBean> getList();

        void clearList();

        void onDestroy();
    }
}
