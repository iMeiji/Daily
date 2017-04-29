package com.meiji.daily.mvp.useradd;

import com.meiji.daily.RetrofitFactory;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.mvp.postslist.PostsListView;
import com.meiji.daily.utils.Api;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/11/27.
 */

class UseraddPresenter implements IUseradd.Presenter {

    private IUseradd.View view;
    private ZhuanlanDao dao = new ZhuanlanDao();
    private List<ZhuanlanBean> list;

    UseraddPresenter(IUseradd.View view) {
        this.view = view;
    }

    @Override
    public void doCheckInputId(final String input) {
        view.onShowRefreshing();
        RetrofitFactory.getRetrofit().create(Api.class).getZhuanlanBeanRx(input)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@NonNull ZhuanlanBean bean) throws Exception {
                        dao.add(TYPE_USERADD, bean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhuanlanBean>() {
                    @Override
                    public void accept(@NonNull ZhuanlanBean bean) throws Exception {
                        view.onAddSuccess();
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
    public void doSetAdapter() {
        Observable
                .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                        list = dao.query(TYPE_USERADD);
                        e.onNext(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuanlanBean>>() {
                    @Override
                    public void accept(@NonNull List<ZhuanlanBean> list) throws Exception {
                        view.onSetAdapter(list);
                        view.onHideRefreshing();
                    }
                });
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onAddFail();
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();
        PostsListView.launch(slug, name, postsCount);
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        doSetAdapter();
        view.onHideRefreshing();
    }

    @Override
    public void doRemoveItem(final int position) {
        final ZhuanlanBean bean = list.get(position);
        dao.removeSlug(bean.getSlug());
        doSetAdapter();
    }

    @Override
    public void doRemoveItemCancel(ZhuanlanBean bean) {
        dao.add(TYPE_USERADD, bean);
        doSetAdapter();
    }
}
