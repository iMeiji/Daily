package com.meiji.daily.module.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Meiji on 2017/5/5.
 */

public interface IBaseView<T> {

//    void setPresenter(T presenter);

    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
}
