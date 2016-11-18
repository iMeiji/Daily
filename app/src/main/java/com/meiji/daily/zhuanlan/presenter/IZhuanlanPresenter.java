package com.meiji.daily.zhuanlan.presenter;

/**
 * Created by Meiji on 2016/11/17.
 */

public interface IZhuanlanPresenter {


    /**
     * 获取专栏类型
     *
     * @param type
     */
    void doGetType(int type);

    /**
     * 请求数据
     *
     * @param ids
     */
    void doRequestData(String ids[]);

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
}

