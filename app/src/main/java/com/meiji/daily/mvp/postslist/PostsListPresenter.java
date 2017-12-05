package com.meiji.daily.mvp.postslist;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.data.remote.IApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/19.
 */
@Deprecated
public class PostsListPresenter implements IPostsList.Presenter {

    private IPostsList.View view;
    private List<PostsListBean> list = new ArrayList<>();

    public PostsListPresenter(IPostsList.View view) {
        this.view = view;
    }

    @Override
    public void doRequestData(final String slug, final int offset) {
        RetrofitFactory.getRetrofit().create(IApi.class).getPostsListRx(slug, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<List<PostsListBean>>bindToLife())
                .subscribe(new Consumer<List<PostsListBean>>() {
                    @Override
                    public void accept(@NonNull List<PostsListBean> postsListBeen) throws Exception {
                        list.addAll(postsListBeen);
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
    public void doRefresh() {
        list.clear();
        view.onRequestData();
    }

    @Override
    public void doSetAdapter() {
        view.onSetAdapter(list);
        view.onHideLoading();
    }

    @Override
    public void onFail() {
        view.onHideLoading();
        view.onShowNetError();
    }
}
