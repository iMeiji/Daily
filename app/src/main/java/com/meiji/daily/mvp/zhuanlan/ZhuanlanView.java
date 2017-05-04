package com.meiji.daily.mvp.zhuanlan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.meiji.daily.utils.ColorUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanView extends RxFragment implements IZhuanlan.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;

    private ZhuanlanAdapter adapter;
    private IZhuanlan.Presenter presenter;

    public static ZhuanlanView newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ZhuanlanView fragment = new ZhuanlanView();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanlan, container, false);
        presenter = new ZhuanlanPresenter(this);
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
        refresh_layout.setColorSchemeColors(ColorUtils.getColor());
        refresh_layout.setOnRefreshListener(this);
    }

    @Override
    public void onRequestData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int type = arguments.getInt("type");
            presenter.doGetType(type);
        }
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        if (adapter == null) {
            adapter = new ZhuanlanAdapter(getActivity(), list);
            recycler_view.setAdapter(adapter);
            adapter.setItemClickListener(new IOnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
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
        recycler_view.setVisibility(View.GONE);
    }

    @Override
    public void onHideRefreshing() {
        refresh_layout.setRefreshing(false);
        recycler_view.setVisibility(View.VISIBLE);
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
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void onStop() {
        presenter.onDestroy();
        super.onStop();
    }


}
