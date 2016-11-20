package com.meiji.daily.mvp.zhuanlan.model;

import com.google.gson.Gson;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanModeImpl implements IZhuanlanModel {

    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_EMOTION = 3;
    public static final int TYPE_FINANCE = 4;
    public static final int TYPE_ZHIHU = 5;
    private String[] ids;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gson = new Gson();
    private List<ZhuanlanBean> list = new ArrayList<>();
    private Call call;

    public ZhuanlanModeImpl() {
    }

    @Override
    public boolean getRequestData(String[] ids) {
        if (list.size() != 0) {
            list.clear();
        }
        boolean flag = false;

        for (int i = 0; i < ids.length; i++) {
            final Request request = new Request.Builder()
                    .url(Api.BASE_URL + ids[i])
                    .get()
                    .build();

            Response response = null;
            try {
                call = okHttpClient.newCall(request);
                response = call.execute();
                if (response.isSuccessful()) {
                    flag = true;
                }
                String responseJson = response.body().string();
                ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
                list.add(bean);
                System.out.println(i + "---" + responseJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public List<ZhuanlanBean> getList() {
        return list;
    }

    @Override
    public void onDestroy() {
        call.cancel();
    }
}
