package com.meiji.daily.zhuanlan.model;

import com.meiji.daily.bean.ZhuanlanBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/17.
 */

public interface IZhuanlanMode {

    boolean getRequestData(String[] ids);

    List<ZhuanlanBean> getList();
}
