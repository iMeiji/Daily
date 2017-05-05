package com.meiji.daily.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Meiji on 2017/5/5.
 */

public interface IBaseView {

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

    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
}
