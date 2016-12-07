package com.meiji.daily.mvp.zhuanlan;

import android.content.Intent;

import com.meiji.daily.InitApp;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.mvp.postslist.PostsListView;

import java.util.List;

import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_SLUG;

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
    public void doGetType(int type) {
        view.onShowRefreshing();
        list = model.getList(type);
        if (list.size() != 0) {
            doSetAdapter(list);
        } else {
            model.getData(type);
        }
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

        Intent intent = new Intent(InitApp.AppContext, PostsListView.class);
        intent.putExtra(ZHUANLANBEAN_SLUG, slug);
        intent.putExtra(ZHUANLANBEAN_NAME, name);
        intent.putExtra(ZHUANLANBEAN_POSTSCOUNT, postsCount);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        view.onHideRefreshing();
        model.onDestroy();
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
