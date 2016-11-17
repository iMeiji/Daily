package com.meiji.daily.zhuanlan.model;

import com.google.gson.Gson;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanModeImpl implements IZhuanlanMode {

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
                response = okHttpClient.newCall(request).execute();
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


            /*okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    *//*Snackbar.make(refreshLayout, "网络不给力", Snackbar.LENGTH_SHORT).show();
                    message = handler.obtainMessage();
                    message.what = 2;
                    message.sendToTarget();*//*
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String responseJson = response.body().string();
                    ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
                    list.add(bean);
                    System.out.println(finalI + "---" + responseJson);
                    if (finalI == ids.length - 1) {
                        *//*message = handler.obtainMessage();
                        message.what = 1;
                        message.sendToTarget();*//*
                    }
                }
            });*/
        }
        return flag;
    }

    @Override
    public List<ZhuanlanBean> getList() {
        return list;
    }
}
