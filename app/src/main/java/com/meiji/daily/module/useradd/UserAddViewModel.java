package com.meiji.daily.module.useradd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.meiji.daily.Constant;
import com.meiji.daily.InitApp;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.RxBus;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/12/4.
 */

public class UserAddViewModel extends AndroidViewModel {

    private Observable<Boolean> mRxBus;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsRefreshUI;
    private MutableLiveData<Boolean> mIsAddResult;
    private MutableLiveData<List<ZhuanlanBean>> mList;
    private CompositeDisposable mDisposable;

    {
        mIsLoading = new MutableLiveData<>();
        mIsRefreshUI = new MutableLiveData<>();
        mIsAddResult = new MutableLiveData<>();
        mList = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();

        mIsLoading.setValue(true);
        mIsRefreshUI.setValue(true);
    }

    public UserAddViewModel(@NonNull Application application) {
        super(application);

        handleData();
        subscribeTheme();
    }

    public MutableLiveData<List<ZhuanlanBean>> getList() {
        return mList;
    }

    public MutableLiveData<Boolean> isRefreshUI() {
        return mIsRefreshUI;
    }

    public MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> isAddResult() {
        return mIsAddResult;
    }

    void handleData() {
        mIsLoading.setValue(true);

        Disposable subscribe = InitApp.sDatabase.ZhuanlanNewDao().queryRx(Constant.TYPE_USERADD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(List<ZhuanlanBean> list) throws Exception {
                        mList.setValue(list);
                    }
                }, ErrorAction.error());
        mDisposable.add(subscribe);
        mIsLoading.setValue(false);
    }

    void addItem(final String input) {
        mIsLoading.setValue(true);

        Disposable subscribe = RetrofitFactory.getRetrofit().create(IApi.class).getZhuanlanBeanRx(input)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(ZhuanlanBean bean) throws Exception {
                        if (bean != null) {
                            bean.setType(Constant.TYPE_USERADD);
                            InitApp.sDatabase.ZhuanlanNewDao().insert(bean);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull ZhuanlanBean bean) throws Exception {
                        mIsAddResult.setValue(true);
                        handleData();
                    }
                }, new ErrorAction() {
                    @Override
                    public void doAction() {
                        mIsAddResult.setValue(false);
                        mIsLoading.setValue(false);
                    }
                }.action());
        mDisposable.add(subscribe);
    }

    private void subscribeTheme() {
        mRxBus = RxBus.getInstance().register(Constant.RxBusEvent.REFRESHUI);
        Disposable subscribe = mRxBus.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mIsRefreshUI.setValue(mIsRefreshUI.getValue() != null && !mIsRefreshUI.getValue());
            }
        }, ErrorAction.error());
        mDisposable.add(subscribe);
    }

    void deleteItem(final ZhuanlanBean bean) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Object> e) throws Exception {
                InitApp.sDatabase.ZhuanlanNewDao().delete(bean.getSlug());
            }
        }).subscribeOn(Schedulers.io()).subscribe();
        mDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        RxBus.getInstance().unregister(Constant.RxBusEvent.REFRESHUI, mRxBus);
        mDisposable.clear();
        super.onCleared();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        final Application mApplication;

        public Factory(@NonNull Application application) {
            this.mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserAddViewModel(mApplication);
        }
    }
}

