package com.meiji.daily.module.useradd;

import com.meiji.daily.Constant;
import com.meiji.daily.InitApp;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.remote.IApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/27.
 */
@Deprecated
public class UserAddPresenter implements IUserAdd.Presenter {

    private IUserAdd.View view;
    //    private ZhuanlanDao dao = new ZhuanlanDao();
    private List<ZhuanlanBean> list;

    public UserAddPresenter(IUserAdd.View view) {
        this.view = view;
    }

    @Override
    public void doCheckInputId(final String input) {
        view.onShowLoading();
        RetrofitFactory.getRetrofit().create(IApi.class).getZhuanlanBeanRx(input)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@NonNull ZhuanlanBean bean) throws Exception {
                        bean.setType(Constant.TYPE_USERADD);
                        InitApp.sDatabase.ZhuanlanNewDao().insert(bean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<ZhuanlanBean>bindToLife())
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@NonNull ZhuanlanBean bean) throws Exception {
                        view.onAddSuccess();
                        doSetAdapter();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        onFail();
                    }
                });
    }

    @Override
    public void doSetAdapter() {
        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        list = InitApp.sDatabase.ZhuanlanNewDao().query(Constant.TYPE_USERADD);
                        e.onNext(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<ZhuanlanBean>>bindToLife())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        view.onSetAdapter(list);
                        view.onHideLoading();
                    }
                });
    }

    @Override
    public void onFail() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doRefresh() {
        view.onShowLoading();
        doSetAdapter();
    }

    @Override
    public void doRemoveItem(final int position) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                final ZhuanlanBean bean = list.get(position);
                InitApp.sDatabase.ZhuanlanNewDao().delete(bean.getSlug());
                doSetAdapter();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void doRemoveItemCancel(final ZhuanlanBean bean) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                bean.setType(Constant.TYPE_USERADD);
                InitApp.sDatabase.ZhuanlanNewDao().insert(bean);
                doSetAdapter();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
