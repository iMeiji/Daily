package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.mvp.postslist.PostsListView;
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

    private IZhuanlan.View view;
    //    private IZhuanlan.Model model;
    private List<ZhuanlanBean> list;
    private ZhuanlanDao dao = new ZhuanlanDao();
    private Call<ZhuanlanBean> call;
    private String[] ids;


    ZhuanlanPresenter(IZhuanlan.View view) {
        this.view = view;
//        this.model = new ZhuanlanModel(this);
    }

    @Override
    public void doGetType(final int type) {
        view.onShowRefreshing();

        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        e.onNext(dao.query(type));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<ZhuanlanBean>, Observable<List<ZhuanlanBean>>>() {
                    @Override
                    public Observable<List<ZhuanlanBean>> apply(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (list.size() != 0) {
                            return Observable.just(list);
                        } else {
                            list = retrofitRequest(type);
                            return Observable.just(list);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<ZhuanlanBean>>bindToLife())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (list.size() != 0) {
                            doSetAdapter(list);
                        } else {
                            onFail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        onFail();
                    }
                });
    }

    private List<ZhuanlanBean> retrofitRequest(int type) {

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

        for (ZhuanlanBean bean : list) {
            dao.add(type, bean);
        }

        return list;
    }

    @Override
    public void doSetAdapter(List<ZhuanlanBean> list) {
        this.list = list;
        view.onSetAdapter(list);
        view.onHideRefreshing();
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();
        PostsListView.launch(slug, name, postsCount);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        view.onRequestData();
    }

    @Override
    public void onDestroy() {
        if (call != null && call.isCanceled()) {
            call.cancel();
        }
    }
}
