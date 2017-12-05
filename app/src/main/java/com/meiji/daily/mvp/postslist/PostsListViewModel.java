package com.meiji.daily.mvp.postslist;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2017/12/5.
 */

public class PostsListViewModel extends AndroidViewModel {

    private String mSlug;
    private int mPostCount;
    private List<PostsListBean> mList;

    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<Boolean> mIsEnd;
    private MutableLiveData<Integer> mOffset;
    private LiveData<List<PostsListBean>> mListLiveData;
    private CompositeDisposable mDisposable;

    {
        mList = new ArrayList<>();
        mDisposable = new CompositeDisposable();
        mIsLoading = new MutableLiveData<>();
        mIsEnd = new MutableLiveData<>();
        mListLiveData = new MutableLiveData<>();
        mOffset = new MutableLiveData<>();
    }

    PostsListViewModel(@NonNull Application application, String slug, int postCount) {
        super(application);
        this.mSlug = slug;
        this.mPostCount = postCount;

        mIsLoading.setValue(true);
        mOffset.setValue(0);

        // 当 mOffset 的值发生改变，就会执行 apply
        mListLiveData = Transformations.switchMap(mOffset, new Function<Integer, LiveData<List<PostsListBean>>>() {
            @Override
            public LiveData<List<PostsListBean>> apply(Integer input) {
                return getData(input);
            }
        });
    }

    public LiveData<List<PostsListBean>> getListLiveData() {
        return mListLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsEnd() {
        return mIsEnd;
    }

    LiveData<List<PostsListBean>> getData(final int offset) {

        final MutableLiveData<List<PostsListBean>> liveData = new MutableLiveData<>();

        Disposable subscribe = RetrofitFactory.getRetrofit().create(IApi.class).getPostsListRx(mSlug, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PostsListBean>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<PostsListBean> list) throws Exception {
                        mList.addAll(list);
                        liveData.setValue(mList);
                    }
                }, new ErrorAction() {
                    @Override
                    public void doAction() {
                        liveData.setValue(null);
                    }
                }.action());
        mDisposable.add(subscribe);
        mIsLoading.setValue(false);
        return liveData;
    }

    public void doRefresh() {
        if (mList != null) {
            mList.clear();
        }
        mOffset.setValue(0);
    }

    void loadMore() {
        if (mOffset.getValue() != null && mOffset.getValue() >= mPostCount - 1) {
            mIsEnd.setValue(true);
            return;
        }

        if (mList != null) {
            mOffset.setValue(mList.size());
        } else {
            mOffset.setValue(0);
        }
    }

    @Override
    protected void onCleared() {
        mDisposable.clear();
        super.onCleared();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final String mSlug;
        private final int mPostCount;

        public Factory(@NonNull Application application, @NonNull String slug, int postCount) {
            this.mApplication = application;
            this.mSlug = slug;
            this.mPostCount = postCount;
        }

        @android.support.annotation.NonNull
        @Override
        public <T extends ViewModel> T create(@android.support.annotation.NonNull Class<T> modelClass) {
            return (T) new PostsListViewModel(mApplication, mSlug, mPostCount);
        }
    }
}
