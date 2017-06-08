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
import com.meiji.daily.mvp.base.BaseFragment;
import com.meiji.daily.utils.ColorUtils;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanView extends BaseFragment<IZhuanlan.Presenter> implements IZhuanlan.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_layout;
    private int type;

    public static ZhuanlanView newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
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
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeColors(ColorUtils.getColor());
        refresh_layout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
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
            recycler_view.setAdapter(adapter);
//          点击事件放到 ItemViewBinder 里
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
        refresh_layout.setRefreshing(true);
        recycler_view.setVisibility(View.GONE);
    }

    @Override
    public void onHideLoading() {
        refresh_layout.setRefreshing(false);
        recycler_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        refresh_layout.setEnabled(true);
    }

    @Override
    public void onStop() {
        presenter.onDestroy();
        super.onStop();
    }

    @Override
    public void setPresenter(IZhuanlan.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new ZhuanlanPresenter(this);
        }
    }
}
