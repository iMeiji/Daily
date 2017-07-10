package com.meiji.daily.mvp.zhuanlan;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.injector.component.DaggerZhuanlanComponent;
import com.meiji.daily.injector.module.ZhuanlanModule;
import com.meiji.daily.mvp.base.BaseFragment;
import com.meiji.daily.util.ColorUtil;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanView extends BaseFragment<IZhuanlan.Presenter> implements IZhuanlan.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ZhuanlanView";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private int type;

    public static ZhuanlanView newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TAG, type);
        ZhuanlanView fragment = new ZhuanlanView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_zhuanlan;
    }

    @Override
    protected void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeColors(ColorUtil.getColor());
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt(TAG);
        }
        onRequestData();
    }

    @Override
    public void onRequestData() {
        presenter.doGetType(type);
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        if (adapter == null) {
            adapter = new MultiTypeAdapter(list);
            adapter.register(ZhuanlanBean.class, new ZhuanlanViewBinder());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(refreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        refreshLayout.setEnabled(true);
    }

    @Override
    public void onStop() {
        presenter.onDestroy();
        super.onStop();
    }

    @Override
    public void initInjector() {
        DaggerZhuanlanComponent.builder()
                .zhuanlanModule(new ZhuanlanModule(this))
                .build()
                .inject(this);
    }
}
