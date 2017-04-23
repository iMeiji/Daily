package com.meiji.daily.mvp.zhuanlan;

import android.os.Handler;
import android.os.Message;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanModel implements IZhuanlan.Model {

    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_EMOTION = 3;
    public static final int TYPE_FINANCE = 4;
    public static final int TYPE_ZHIHU = 5;
    public static final int TYPE_USERADD = 6;
    private static final String TAG = "ZhuanlanModel";
    private IZhuanlan.Presenter presenter;
    private ZhuanlanDao zhuanlanDao;
    private String[] ids;
    private int type;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                presenter.doSetAdapter(getList(type));
            }
            if (message.what == 0) {
                presenter.onFail();
            }
            return false;
        }
    });
    private Call<ZhuanlanBean> call;

    ZhuanlanModel(IZhuanlan.Presenter presenter) {
        this.presenter = presenter;
        this.zhuanlanDao = new ZhuanlanDao();
    }

    @Override
    public void getData(int type) {
        this.type = type;
        switch (type) {
            case TYPE_PRODUCT:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.product);
                break;
            case TYPE_MUSIC:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.music);
                break;
            case TYPE_LIFE:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.life);
                break;
            case TYPE_EMOTION:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.emotion);
                break;
            case TYPE_FINANCE:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.profession);
                break;
            case TYPE_ZHIHU:
                ids = InitApp.AppContext.getResources().getStringArray(R.array.zhihu);
                break;
        }

        List<ZhuanlanBean> list = zhuanlanDao.query(this.type);
        if (list.size() != ids.length) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ZhuanlanBean> requestList = retrofitRequest(ids);
                    if (requestList.size() != 0) {
                        saveData(requestList);
                        Message message = handler.obtainMessage(1);
                        message.sendToTarget();
                    } else {
                        // error network
                        Message message = handler.obtainMessage(0);
                        message.sendToTarget();
                    }
                }
            }).start();
        }
    }

    @Override
    public List<ZhuanlanBean> getList(int type) {
        return zhuanlanDao.query(type);
    }

    private void saveData(List<ZhuanlanBean> list) {
        for (ZhuanlanBean bean : list) {
            String type = String.valueOf(this.type);
            String avatarUrl = bean.getAvatar().getTemplate();
            String avatarId = bean.getAvatar().getId();
            String name = bean.getName();
            String followersCount = String.valueOf(bean.getFollowersCount());
            String postsCount = String.valueOf(bean.getPostsCount());
            String intro = bean.getIntro();
            String slug = bean.getSlug();
            zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
        }
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public List<ZhuanlanBean> retrofitRequest(String[] ids) {
        List<ZhuanlanBean> list = new ArrayList<>();
        Api api = RetrofitFactory.getRetrofit().create(Api.class);
        for (String id : ids) {
            call = api.getZhuanlanBean(id);
            try {
                Response<ZhuanlanBean> response = call.execute();
                if (response.isSuccessful()) {
                    list.add(response.body());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
