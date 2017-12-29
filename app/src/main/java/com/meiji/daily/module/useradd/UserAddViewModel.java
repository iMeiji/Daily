package com.meiji.daily.module.useradd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.meiji.daily.Constant;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;
import com.meiji.daily.util.RxBusHelper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/4.
 */

public class UserAddViewModel extends AndroidViewModel implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final AppDatabase mAppDatabase;
    private final Retrofit mRetrofit;
    private final RxBusHelper mRxBusHelper;
    private final CompositeDisposable mDisposable;

    private Flowable<Boolean> mRxBus;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsRefreshUI;
    private MutableLiveData<Boolean> mIsAddSuccess;
    private MutableLiveData<List<ZhuanlanBean>> mList;

    {
        mIsLoading = new MutableLiveData<>();
        mIsRefreshUI = new MutableLiveData<>();
        mIsAddSuccess = new MutableLiveData<>();
        mList = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();

        mIsLoading.setValue(true);
        mIsRefreshUI.setValue(true);
    }

    UserAddViewModel(Application application, AppDatabase appDatabase,
                     Retrofit retrofit, RxBusHelper rxBusHelper) {
        super(application);
        mAppDatabase = appDatabase;
        mRetrofit = retrofit;
        mRxBusHelper = rxBusHelper;

        handleData();
        subscribeTheme();
    }

    public MutableLiveData<List<ZhuanlanBean>> getList() {
        return mList;
    }

    MutableLiveData<Boolean> isRefreshUI() {
        return mIsRefreshUI;
    }

    MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    MutableLiveData<Boolean> isAddResult() {
        return mIsAddSuccess;
    }

    void handleData() {
        mIsLoading.setValue(true);

        Disposable subscribe = mAppDatabase.ZhuanlanNewDao().query(Constant.TYPE_USERADD)
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

        Disposable subscribe = mRetrofit.create(IApi.class).getZhuanlanBean(input)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(ZhuanlanBean bean) throws Exception {
                        if (bean != null) {
                            bean.setType(Constant.TYPE_USERADD);
                            mAppDatabase.ZhuanlanNewDao().insert(bean);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull ZhuanlanBean bean) throws Exception {
                        mIsAddSuccess.setValue(true);
                        handleData();
                    }
                }, new ErrorAction() {
                    @Override
                    public void doAction() {
                        mIsAddSuccess.setValue(false);
                        mIsLoading.setValue(false);
                    }
                }.action());
        mDisposable.add(subscribe);
    }

    private void subscribeTheme() {
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI);
        Disposable subscribe = mRxBus.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mIsRefreshUI.setValue(mIsRefreshUI.getValue() != null && !mIsRefreshUI.getValue());
            }
        }, ErrorAction.error());
        mDisposable.add(subscribe);
    }

    void deleteItem(final ZhuanlanBean bean) {
        Disposable subscribe = Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> e) throws Exception {
                mAppDatabase.ZhuanlanNewDao().delete(bean.getSlug());
            }
        }).subscribeOn(Schedulers.io()).subscribe();
        mDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus);
        mDisposable.clear();
        super.onCleared();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final AppDatabase mAppDatabase;
        private final Retrofit mRetrofit;
        private final RxBusHelper mRxBusHelper;

        Factory(Application application, AppDatabase appDatabase,
                Retrofit retrofit, RxBusHelper rxBusHelper) {
            mApplication = application;
            mAppDatabase = appDatabase;
            mRetrofit = retrofit;
            mRxBusHelper = rxBusHelper;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserAddViewModel(mApplication, mAppDatabase, mRetrofit, mRxBusHelper);
        }
    }
}

