package com.meiji.daily.zhuanlan.view;

import com.meiji.daily.bean.ZhuanlanBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/17.
 */

public interface IZhuanlanView {


    void onRequestData();

    void onSetAdapter(List<ZhuanlanBean> list);

    void onShowRefreshing();

    void onHideRefreshing();

    void onFail();
}
