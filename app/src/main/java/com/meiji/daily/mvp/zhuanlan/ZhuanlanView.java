package com.meiji.daily.mvp.zhuanlan;

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
import com.meiji.daily.adapter.ZhuanlanAdapter;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.interfaces.IOnItemClickListener;

import java.util.List;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanView extends Fragment implements IZhuanlan.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;

    private int type;
    private ZhuanlanAdapter adapter;
    private IZhuanlan.Presenter presenter;

    public ZhuanlanView() {
    }

    public static ZhuanlanView newInstance(int type) {
        ZhuanlanView view = new ZhuanlanView();
        view.setType(type);
        return view;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanlan, container, false);
        presenter = new ZhuanlanPresenter(this, getActivity());
        initViews(view);
        onRequestData();
        return view;
    }

    private void initViews(android.view.View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 设置手指在屏幕上下拉多少距离开始刷新
        refresh_layout.setDistanceToTriggerSync(300);
        // 设置下拉刷新按钮的背景颜色
        refresh_layout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉刷新按钮的大小
        refresh_layout.setSize(SwipeRefreshLayout.DEFAULT);
        refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onRequestData() {
        presenter.doGetType(this.type);
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        if (adapter == null) {
            adapter = new ZhuanlanAdapter(getActivity(), list);
            recycler_view.setAdapter(adapter);
            adapter.setItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(android.view.View view, int position) {
                    presenter.doOnClickItem(position);
                }
            });
        } else {
            adapter.notifyItemInserted(list.size());
        }
    }

    @Override
    public void onShowRefreshing() {
        refresh_layout.setRefreshing(true);
        recycler_view.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onHideRefreshing() {
        refresh_layout.setRefreshing(false);
        recycler_view.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onFail() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        refresh_layout.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
