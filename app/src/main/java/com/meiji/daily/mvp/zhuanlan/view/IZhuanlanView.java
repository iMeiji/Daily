package com.meiji.daily.mvp.zhuanlan.view;

import com.meiji.daily.mvp.zhuanlan.model.ZhuanlanBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/17.
 */

public interface IZhuanlanView {

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
