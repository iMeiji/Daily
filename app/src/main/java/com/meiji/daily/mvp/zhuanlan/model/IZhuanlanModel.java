package com.meiji.daily.mvp.zhuanlan.model;

import java.util.List;

/**
 * Created by Meiji on 2016/11/17.
 */

public interface IZhuanlanModel {

    boolean getRequestData(String[] ids);

    List<ZhuanlanBean> getList();

    void onDestroy();
}
