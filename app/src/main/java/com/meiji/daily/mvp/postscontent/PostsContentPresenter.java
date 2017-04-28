package com.meiji.daily.mvp.postscontent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/23.
 */

class PostsContentPresenter implements IPostsContent.Presenter {

    private IPostsContent.View view;
    private IPostsContent.Model model;

    PostsContentPresenter(IPostsContent.View view) {
        this.view = view;
        this.model = new PostsContentModel();
    }

    @Override
    public void doRequestData(final int slug) {
        Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                        e.onNext(model.retrofitRequest(slug));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            doSetWebView();
                        } else {
                            onFail();
                        }
                    }
                });
    }

    @Override
    public void doSetWebView() {
        view.onSetWebView(model.getContent());
    }

    @Override
    public void onFail() {
        view.onFail();
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }
}
