package com.meiji.daily.mvp.postslist;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.mvp.postscontent.PostsContentView;
import com.meiji.daily.utils.IApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/19.
 */

class PostsListPresenter implements IPostsList.Presenter {

    private IPostsList.View view;
    private List<PostsListBean> list = new ArrayList<>();

    PostsListPresenter(IPostsList.View view) {
        this.view = view;
    }

    @Override
    public void doRequestData(final String slug, final int offset) {
        view.onShowLoading();

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
        view.onHideLoading();
        view.onSetAdapter(list);
    }

    @Override
    public void doOnClickItem(int position) {
        String titleImage = list.get(position).getTitleImage();
        String title = list.get(position).getTitle();
        int slug = list.get(position).getSlug();
        PostsContentView.launch(titleImage, title, slug);
    }

    @Override
    public void onFail() {
        view.onHideLoading();
        view.onShowNetError();
    }
}
