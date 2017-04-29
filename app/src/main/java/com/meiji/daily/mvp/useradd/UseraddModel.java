package com.meiji.daily.mvp.useradd;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/11/27.
 */
@Deprecated
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
            doSaveInputId(bean);
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

    private void doSaveInputId(ZhuanlanBean bean) {
        new ZhuanlanDao().add(TYPE_USERADD, bean);
    }
}
