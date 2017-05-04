package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.bean.ZhuanlanBean;
import com.trello.rxlifecycle2.LifecycleTransformer;

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

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }

    interface Presenter {

        /**
         * 获取专栏类型
         */
        void doGetType(int type);

        /**
         * 设置适配器
         */
        void doSetAdapter(List<ZhuanlanBean> list);

        /**
         * 获取点击栏信息
         */
        void doOnClickItem(int position);

        /**
         * 显示查询数据失败
         */
        void onFail();

        /**
         * 正在刷新
         */
        void doRefresh();

        void onDestroy();
    }

    @Deprecated
    interface Model {


        /**
         * 返回数据
         *
         * @param type
         */
        List<ZhuanlanBean> getList(int type);

        void getData(int type);

        List<ZhuanlanBean> retrofitRequest(String[] ids);

        void onDestroy();
    }
}
