package com.meiji.daily.mvp.useradd;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.utils.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Meiji on 2016/11/27.
 */

class UseraddModel implements IUseradd.Model {

    private ZhuanlanBean bean;
    private Call<ZhuanlanBean> call;

    @Override
    public boolean retrofitRequest(String slug) {
        Api api = RetrofitFactory.getRetrofit().create(Api.class);
        call = api.getZhuanlanBean(slug.toLowerCase());
        try {
            Response<ZhuanlanBean> response = call.execute();
            if (response.code() == 404) {
                return false;
            }
            bean = response.body();
        } catch (IOException e) {
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
    public ZhuanlanBean getBean() {
        return bean;
    }
}
