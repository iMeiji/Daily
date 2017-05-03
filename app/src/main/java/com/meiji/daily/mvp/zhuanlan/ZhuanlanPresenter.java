package com.meiji.daily.mvp.zhuanlan;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.mvp.postslist.PostsListView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/11/17.
 */

class ZhuanlanPresenter implements IZhuanlan.Presenter {

    private IZhuanlan.View view;
    private IZhuanlan.Model model;
    private List<ZhuanlanBean> list;

    ZhuanlanPresenter(IZhuanlan.View view) {
        this.view = view;
        this.model = new ZhuanlanModel(this);
    }

    @Override
    public void doGetType(final int type) {
        view.onShowRefreshing();

        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        list = model.getList(type);
                        e.onNext(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        if (list.size() != 0) {
                            doSetAdapter(list);
                        } else {
                            model.getData(type);
                        }
                    }
                });
    }

    @Override
    public void doSetAdapter(List<ZhuanlanBean> list) {
        this.list = list;
        view.onSetAdapter(list);
        view.onHideRefreshing();
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();
        PostsListView.launch(slug, name, postsCount);
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        view.onRequestData();
    }
}
