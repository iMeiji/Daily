package com.meiji.daily.module.zhuanlan;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.module.base.IBasePresenter;
import com.meiji.daily.module.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/11/24.
 */
@Deprecated
public interface IZhuanlan {

    interface View extends IBaseView<Presenter> {

        /**
         * 请求数据
         */
        void onRequestData();

        /**
         * 设置适配器
         */
        void onSetAdapter(List<ZhuanlanBean> list);

        /**
         * 显示加载动画
         */
        void onShowLoading();

        /**
         * 隐藏加载
         */
        void onHideLoading();

        /**
         * 显示网络错误
         */
        void onShowNetError();
    }

    interface Presenter extends IBasePresenter {

        /**
         * 获取专栏类型
         */
        void doLoading();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<ZhuanlanBean> list);

        /**
         * 显示查询数据失败
         */
        void doShowFail();

        /**
         * 正在刷新
         */
        void doRefresh();

        /**
         * 取消网络请求
         */
        void onDestroy();
    }
}