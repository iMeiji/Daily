package com.meiji.daily.mvp.zhuanlan;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiji.daily.R;
import com.meiji.daily.adapter.IOnItemClickListener;
import com.meiji.daily.adapter.ZhuanlanAdapter;
import com.meiji.daily.mvp.zhuanlan.model.ZhuanlanBean;
import com.meiji.daily.mvp.zhuanlan.presenter.IZhuanlanPresenter;
import com.meiji.daily.mvp.zhuanlan.presenter.ZhuanlanPresenterImpl;
import com.meiji.daily.mvp.zhuanlan.view.IZhuanlanView;

import java.util.List;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanViewImpl extends Fragment implements IZhuanlanView, SwipeRefreshLayout.OnRefreshListener {

    private int type;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhuanlanAdapter adapter;

    private IZhuanlanPresenter presenter;
    private Context mContext;

    public ZhuanlanViewImpl(int type) {
        this.type = type;
    }

    public static ZhuanlanViewImpl newInstance(int type) {
        return new ZhuanlanViewImpl(type);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanlan, container, false);
        initViews(view);
        onShowRefreshing();
        onRequestData();
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        // 设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setOnRefreshListener(this);
        mContext = getActivity();
        presenter = new ZhuanlanPresenterImpl(this, mContext);
    }

    @Override
    public void onRequestData() {
        presenter.doGetType(this.type);
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        onHideRefreshing();
        if (adapter == null) {
            adapter = new ZhuanlanAdapter(getActivity(), list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                presenter.doOnClickItem(position);
            }
        });
    }

    @Override
    public void onShowRefreshing() {
        refreshLayout.setRefreshing(true);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onHideRefreshing() {
        refreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        onShowRefreshing();
        onRequestData();
    }

    @Override
    public void onFail() {
        onHideRefreshing();
        Snackbar.make(refreshLayout, "网络不给力", Snackbar.LENGTH_SHORT).show();
        refreshLayout.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        onHideRefreshing();
        presenter.onDestroy();
        super.onDestroy();
    }
}
