package com.meiji.daily.zhuanlan.view;

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
import com.meiji.daily.adapter.ZhuanlanAdapter;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.zhuanlan.presenter.IZhuanlanPresenter;
import com.meiji.daily.zhuanlan.presenter.ZhuanlanPresenterImpl;

import java.util.List;

import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_EMOTION;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_FINANCE;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_LIFE;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_MUSIC;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_PRODUCT;
import static com.meiji.daily.zhuanlan.model.ZhuanlanModeImpl.TYPE_ZHIHU;


/**
 * Created by Meiji on 2016/11/17.
 */

public class ZhuanlanViewImpl extends Fragment implements IZhuanlanView, SwipeRefreshLayout.OnRefreshListener {

    private int type;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhuanlanAdapter adapter;

    private IZhuanlanPresenter presenter;
    private String[] ids;
    private Context mContext;

    public ZhuanlanViewImpl() {

    }

    public static ZhuanlanViewImpl getInstance() {
        return new ZhuanlanViewImpl();
    }

    public int getType() {
        return type;
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
        View view = inflater.inflate(R.layout.fragment_universal, container, false);
        initViews(view);
        switch (type) {
            default:
            case TYPE_PRODUCT:
                ids = getActivity().getResources().getStringArray(R.array.product);
                break;
            case TYPE_MUSIC:
                ids = getActivity().getResources().getStringArray(R.array.music);
                break;
            case TYPE_LIFE:
                ids = getActivity().getResources().getStringArray(R.array.life);
                break;
            case TYPE_EMOTION:
                ids = getActivity().getResources().getStringArray(R.array.emotion);
                break;
            case TYPE_FINANCE:
                ids = getActivity().getResources().getStringArray(R.array.profession);
                break;
            case TYPE_ZHIHU:
                ids = getActivity().getResources().getStringArray(R.array.zhihu);
                break;
        }
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
        presenter = new ZhuanlanPresenterImpl(this);
        mContext = getActivity();
    }

    @Override
    public void onRequestData() {
        presenter.doRequestData(ids);
    }

    @Override
    public void onSetAdapter(List<ZhuanlanBean> list) {
        for (int i = 0; i < list.size(); i++) {
            ZhuanlanBean bean = list.get(i);
            System.out.println(bean.getName());
        }
        onHideRefreshing();
//        recyclerView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new ZhuanlanAdapter(getActivity(), list);
        }
        recyclerView.setAdapter(adapter);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onHideRefreshing();
    }
}
