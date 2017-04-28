package com.meiji.daily.mvp.postslist;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Meiji on 2016/11/19.
 */
@Deprecated
class PostsListModel implements IPostsList.Model {

    private List<PostsListBean> list = new ArrayList<>();
    private Call<List<PostsListBean>> call;

    @Override
    public List<PostsListBean> getList() {
        return this.list;
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public boolean retrofitRequest(String slug, int offset) {
        boolean flag = false;
        Api api = RetrofitFactory.getRetrofit().create(Api.class);
        call = api.getPostsList(slug, offset);
        try {
            Response<List<PostsListBean>> response = call.execute();
            if (response.isSuccessful()) {
                list.addAll(response.body());
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
