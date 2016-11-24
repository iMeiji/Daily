package com.meiji.daily.mvp.zhuanlan;

import java.util.List;

/**
 * Created by Meiji on 2016/11/24.
 */

public interface IZhuanlan {

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
        void onSetAdapter(List<ZhuanlanBean> list);

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
         * 获取专栏类型
         */
        void doGetType(int type);

        /**
         * 请求数据
         */
        void doRequestData(String ids[]);

        /**
         * 设置适配器
         */
        void doSetAdapter();

        /**
         * 获取点击栏信息
         */
        void doOnClickItem(int position);

        void onDestroy();

        void onFail();

        void doRefresh();
    }

    interface Model {

        boolean getRequestData(String[] ids);

        List<ZhuanlanBean> getList();

        void onDestroy();
    }
}
