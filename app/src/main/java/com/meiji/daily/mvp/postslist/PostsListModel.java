package com.meiji.daily.mvp.postslist;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Meiji on 2016/11/19.
 */

class PostsListModel implements IPostsList.Model {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gson = new Gson();
    private List<PostsListBean> list = new ArrayList<>();
    private Call call;

    @Override
    public boolean getRequestData(String url) {
        boolean flag = false;
        Response response;
        Request request;
        try {
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            call = okHttpClient.newCall(request);
            response = call.execute();
            if (response.isSuccessful()) {
                flag = true;
            }
            String responseJson = response.body().string();
            JSONArray jsonArray = new JSONArray(responseJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PostsListBean bean = gson.fromJson(jsonObject.toString(), PostsListBean.class);
                list.add(bean);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<PostsListBean> getList() {
        return this.list;
    }

    @Override
    public void clearList() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
        if (list.size() != 0) {
            list.clear();
        }
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }
}
