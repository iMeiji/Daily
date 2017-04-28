package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
    private Call<ZhuanlanBean> call;

    ZhuanlanModel(IZhuanlan.Presenter presenter) {
        this.presenter = presenter;
        this.zhuanlanDao = new ZhuanlanDao();
    }

    @Override
    public void getData(final int type) {
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

        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        e.onNext(retrofitRequest(ids));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (list.size() != 0) {
                            saveData(list);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (list.size() != 0) {
                            presenter.doSetAdapter(list);
                        } else {
                            presenter.onFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        presenter.onFail();
                    }
                });
    }

    @Override
    public List<ZhuanlanBean> getList(int type) {
        return zhuanlanDao.query(type);
    }

    private void saveData(List<ZhuanlanBean> list) {
        for (ZhuanlanBean bean : list) {
            zhuanlanDao.add(this.type, bean);
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
