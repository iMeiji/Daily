package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.IApi;
import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanPresenter implements IZhuanlan.Presenter {

    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_EMOTION = 3;
    public static final int TYPE_FINANCE = 4;
    public static final int TYPE_ZHIHU = 5;
    public static final int TYPE_USERADD = 6;
    private static final String TAG = "ZhuanlanPresenter";
    private IZhuanlan.View view;
    //    private ZhuanlanDao dao = new ZhuanlanDao();
    private Call<ZhuanlanBean> call;
    private String[] ids;
    private int type;

    public ZhuanlanPresenter(IZhuanlan.View view, int type) {
        this.view = view;
        this.type = type;
    }

    @Override
    public void doLoading() {
        view.onShowLoading();

        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        e.onNext(InitApp.db.ZhuanlanNewDao().query(type));
                    }
                })
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<List<ZhuanlanBean>, Observable<List<ZhuanlanBean>>>() {
                    @Override
                    public Observable<List<ZhuanlanBean>> apply(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            return Observable.just(list);
                        } else {
                            list = retrofitRequest();
                            return Observable.just(list);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<ZhuanlanBean>>bindToLife())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            doSetAdapter(list);
                        } else {
                            doShowFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowFail();
                    }
                });
    }

    private List<ZhuanlanBean> retrofitRequest() {

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

        final List<ZhuanlanBean> list = new ArrayList<>();

        IApi IApi = RetrofitFactory.getRetrofit().create(IApi.class);
        for (String id : ids) {
            call = IApi.getZhuanlanBean(id);
            try {
                Response<ZhuanlanBean> response = call.execute();
                if (response.isSuccessful()) {
                    ZhuanlanBean bean = response.body();
                    if (bean != null) {
                        bean.setType(type);
                        list.add(bean);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InitApp.db.ZhuanlanNewDao().insert(list);
//        for (ZhuanlanBean bean : list) {
//            dao.add(type, bean);
//        }

        return list;
    }

    @Override
    public void doSetAdapter(List<ZhuanlanBean> list) {
        view.onSetAdapter(list);
        view.onHideLoading();
    }

    @Override
    public void doShowFail() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doRefresh() {
        view.onShowLoading();
        view.onRequestData();
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }
}
