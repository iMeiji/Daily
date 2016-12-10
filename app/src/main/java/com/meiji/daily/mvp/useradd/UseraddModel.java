package com.meiji.daily.mvp.useradd;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meiji.daily.bean.ZhuanlanBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/27.
 */

class UseraddModel implements IUseradd.Model {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gson = new Gson();
    private Call call;
    private List<ZhuanlanBean> list = new ArrayList<>();

    @Override
    public boolean getRequestData(String url) {

        if (list.size() != 0) {
            list.clear();
        }
        Request request;
        Response response;
        request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            call = okHttpClient.newCall(request);
            response = call.execute();
            if (response.code() == 404) {
                return false;
            }
            String responseJson = response.body().string();
            ZhuanlanBean bean = gson.fromJson(responseJson, ZhuanlanBean.class);
            list.add(bean);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public List<ZhuanlanBean> getList() {
        return list;
    }
}
