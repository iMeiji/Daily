package com.meiji.daily.module.zhuanlan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.meiji.daily.Constant;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.local.AppDatabase;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;
import com.meiji.daily.util.RxBusHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/11/29.
 */

public class ZhuanlanViewModel extends AndroidViewModel {

    static final String TAG = "ZhuanlanViewModel";

    private final AppDatabase mAppDatabase;
    private final Retrofit mRetrofit;
    private final RxBusHelper mRxBusHelper;
    private final CompositeDisposable mDisposable;

    private final int mType;
    private String[] mIdArr;
    private Flowable<Boolean> mRxBus;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsRefreshUI;
    private MutableLiveData<List<ZhuanlanBean>> mList;

    {
        mIsLoading = new MutableLiveData<>();
        mIsRefreshUI = new MutableLiveData<>();
        mList = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();

        mIsLoading.setValue(true);
        mIsRefreshUI.setValue(true);
    }

    private ZhuanlanViewModel(Application application, int type,
                              AppDatabase appDatabase, Retrofit retrofit, RxBusHelper rxBusHelper) {
        super(application);
        mType = type;
        mAppDatabase = appDatabase;
        mRetrofit = retrofit;
        mRxBusHelper = rxBusHelper;

        handleData();
        subscribeTheme();
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

    void handleData() {
        Disposable subscribe = mAppDatabase.ZhuanlanNewDao().query(mType)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<ZhuanlanBean>, MaybeSource<List<ZhuanlanBean>>>() {
                    @Override
                    public MaybeSource<List<ZhuanlanBean>> apply(List<ZhuanlanBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            return Maybe.just(list);
                        } else {
                            list = retrofitRequest();
                            return Maybe.just(list);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(List<ZhuanlanBean> list) throws Exception {
                        mList.setValue(list);
                        mIsLoading.setValue(false);
                    }
                }, new ErrorAction() {
                    @Override
                    public void doAction() {
                        mList.setValue(null);
                        mIsLoading.setValue(false);
                    }
                }.action());
        mDisposable.add(subscribe);
    }

    @Override
    protected void onCleared() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus);
        mDisposable.clear();
        super.onCleared();
    }

    MutableLiveData<List<ZhuanlanBean>> getList() {
        return mList;
    }

    MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    MutableLiveData<Boolean> isRefreshUI() {
        return mIsRefreshUI;
    }

    private List<ZhuanlanBean> retrofitRequest() {

        switch (mType) {
            case Constant.TYPE_PRODUCT:
                mIdArr = getApplication().getResources().getStringArray(R.array.product);
                break;
            case Constant.TYPE_MUSIC:
                mIdArr = getApplication().getResources().getStringArray(R.array.music);
                break;
            case Constant.TYPE_LIFE:
                mIdArr = getApplication().getResources().getStringArray(R.array.life);
                break;
            case Constant.TYPE_EMOTION:
                mIdArr = getApplication().getResources().getStringArray(R.array.emotion);
                break;
            case Constant.TYPE_FINANCE:
                mIdArr = getApplication().getResources().getStringArray(R.array.profession);
                break;
            case Constant.TYPE_ZHIHU:
                mIdArr = getApplication().getResources().getStringArray(R.array.zhihu);
                break;
        }

        final List<ZhuanlanBean> list = new ArrayList<>();
        final List<Maybe<ZhuanlanBean>> maybeList = new ArrayList<>();
        final IApi api = mRetrofit.create(IApi.class);

        for (String id : mIdArr) {
            maybeList.add(api.getZhuanlanBean(id));
        }

        Disposable subscribe = Maybe.merge(maybeList)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mAppDatabase.ZhuanlanNewDao().insert(list);
                    }
                })
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(ZhuanlanBean bean) throws Exception {
                        if (bean != null) {
                            bean.setType(mType);
                            list.add(bean);
                        }
                    }
                }, ErrorAction.error());
        mDisposable.add(subscribe);

        return list;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final int mType;
        private final AppDatabase mAppDatabase;
        private final Retrofit mRetrofit;
        private final RxBusHelper mRxBusHelper;

        public Factory(Application application, int type,
                       AppDatabase appDatabase, Retrofit retrofit, RxBusHelper rxBusHelper) {
            mApplication = application;
            mType = type;
            mAppDatabase = appDatabase;
            mRetrofit = retrofit;
            mRxBusHelper = rxBusHelper;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ZhuanlanViewModel(mApplication, mType, mAppDatabase, mRetrofit, mRxBusHelper);
        }
    }
}
