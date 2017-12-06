package com.meiji.daily.module.zhuanlan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.meiji.daily.Constant;
import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.RxBus;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/11/29.
 */

public class ZhuanlanViewModel extends AndroidViewModel {

    public static final String TAG = "ZhuanlanViewModel";
    private int mType;
    private String[] mIdArr;
    private Observable<Boolean> mRxBus;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsRefreshUI;
    private MutableLiveData<List<ZhuanlanBean>> mList;
    private CompositeDisposable mDisposable;

    {
        mIsLoading = new MutableLiveData<>();
        mIsRefreshUI = new MutableLiveData<>();
        mList = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();

        mIsLoading.setValue(true);
        mIsRefreshUI.setValue(true);
    }

    private ZhuanlanViewModel(@NonNull Application application, int type) {
        super(application);
        mType = type;

        handleData();
        subscribeTheme();
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

    void handleData() {
        Disposable subscribe = InitApp.sDatabase.ZhuanlanNewDao().queryRx(mType)
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<List<ZhuanlanBean>, Publisher<List<ZhuanlanBean>>>() {
                    @Override
                    public Publisher<List<ZhuanlanBean>> apply(List<ZhuanlanBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            return Flowable.just(list);
                        } else {
                            list = retrofitRequest();
                            return Flowable.just(list);
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
        RxBus.getInstance().unregister(Constant.RxBusEvent.REFRESHUI, mRxBus);
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
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.product);
                break;
            case Constant.TYPE_MUSIC:
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.music);
                break;
            case Constant.TYPE_LIFE:
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.life);
                break;
            case Constant.TYPE_EMOTION:
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.emotion);
                break;
            case Constant.TYPE_FINANCE:
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.profession);
                break;
            case Constant.TYPE_ZHIHU:
                mIdArr = InitApp.sAppContext.getResources().getStringArray(R.array.zhihu);
                break;
        }

        final List<ZhuanlanBean> list = new ArrayList<>();
        final List<Observable<ZhuanlanBean>> observableList = new ArrayList<>();
        final IApi api = RetrofitFactory.getRetrofit().create(IApi.class);

        for (String id : mIdArr) {
            observableList.add(api.getZhuanlanBeanRx(id));
        }

        Disposable subscribe = Observable.merge(observableList)
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(ZhuanlanBean bean) throws Exception {
                        if (bean != null) {
                            bean.setType(mType);
                            list.add(bean);
                            InitApp.sDatabase.ZhuanlanNewDao().insert(bean);
                        }
                    }
                }, ErrorAction.error());
        mDisposable.add(subscribe);

        return list;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final int mType;

        public Factory(@NonNull Application application, int type) {
            this.mApplication = application;
            this.mType = type;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ZhuanlanViewModel(mApplication, mType);
        }
    }
}
