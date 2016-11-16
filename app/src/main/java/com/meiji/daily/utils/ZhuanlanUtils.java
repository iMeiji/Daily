package com.meiji.daily.utils;

import com.google.gson.Gson;
import com.meiji.daily.bean.ZhuanlanBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/11/16.
 */

public class ZhuanlanUtils {

    private List<ZhuanlanBean> list = new ArrayList<>();

    public void saveData(String response) {
        Gson gson = new Gson();
        ZhuanlanBean bean = gson.fromJson(response, ZhuanlanBean.class);
        list.add(bean);
    }

    public List<ZhuanlanBean> getList() {
        return list;
    }

    public void setList(List<ZhuanlanBean> list) {
        this.list = list;
    }
}
