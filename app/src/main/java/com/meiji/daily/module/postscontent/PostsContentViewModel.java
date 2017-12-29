package com.meiji.daily.module.postscontent;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.meiji.daily.bean.PostsContentBean;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.ErrorAction;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/5.
 */

public class PostsContentViewModel extends AndroidViewModel {

    private final Retrofit mRetrofit;
    private final CompositeDisposable mDisposable;

    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<String> mHTML;

    {
        mIsLoading = new MutableLiveData<>();
        mHTML = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();
    }

    PostsContentViewModel(Application application, int slug, Retrofit retrofit) {
        super(application);
        mRetrofit = retrofit;

        handleData(slug);
    }

    MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    MutableLiveData<String> getHTML() {
        return mHTML;
    }

    private void handleData(final int slug) {
        mIsLoading.setValue(true);

        Disposable subscribe = mRetrofit.create(IApi.class).getPostsContentBean(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostsContentBean>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull PostsContentBean bean) throws Exception {
                        mIsLoading.setValue(false);
                        mHTML.setValue(parserHTML(bean.getContent()));
                    }
                }, new ErrorAction() {
                    @Override
                    public void doAction() {
                        mIsLoading.setValue(false);
                        mHTML.setValue(null);
                    }
                }.action());

        mDisposable.add(subscribe);
    }

    private String parserHTML(String content) {
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "</body>\n</html>";

        return html;
    }

    @Override
    protected void onCleared() {
        mDisposable.clear();
        super.onCleared();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final int mSlug;
        private final Retrofit mRetrofit;

        Factory(Application application, int slug, Retrofit retrofit) {
            mApplication = application;
            mSlug = slug;
            mRetrofit = retrofit;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PostsContentViewModel(mApplication, mSlug, mRetrofit);
        }
    }
}
