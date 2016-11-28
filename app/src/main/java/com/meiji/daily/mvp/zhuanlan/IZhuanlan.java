package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.bean.ZhuanlanBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/24.
 */

interface IZhuanlan {

    interface View {

        /**
         * 请求数据
         */
        void onRequestData();

        /**
         * 设置适配器
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
         * 请求数据,首先从数据库查询 list ,通过判断 List.size 与 ids.length
         * 相等 -> 设置适配器
         * 不相等 -> 从网络请求数据,判断是否成功
         * 成功 -> 保存到数据库
         * 不成功 -> 显示查询数据失败
         */
        void doRequestData();

        /**
         * 设置适配器
         */
        void doSetAdapter();

        /**
         * 保存到数据库
         */
        void doSaveData();

        /**
         * 获取点击栏信息
         */
        void doOnClickItem(int position);

        /**
         * 结束
         */
        void onDestroy();

        /**
         * 显示查询数据失败
         */
        void onFail();

        /**
         * 正在刷新
         */
        void doRefresh();
    }

    interface Model {

        /**
         * 请求数据
         */
        boolean getRequestData(String[] ids);

        /**
         * 返回数据
         */
        List<ZhuanlanBean> getList();

        /**
         * 结束
         */
        void onDestroy();
    }
}
