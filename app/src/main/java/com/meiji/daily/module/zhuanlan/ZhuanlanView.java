package com.meiji.daily.module.zhuanlan;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiji.daily.App;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.di.component.DaggerZhuanlanComponent;
import com.meiji.daily.di.module.ZhuanlanModule;
import com.meiji.daily.module.base.BaseFragment;
import com.meiji.daily.util.RecyclerViewUtil;
import com.meiji.daily.util.SettingUtil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Meiji on 2017/11/29.
 */

public class ZhuanlanView extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ZhuanlanView";
    private static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";
    @Inject
    ZhuanlanViewModel mModel;
    @Inject
    @Named("application")
    Application mApplication;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mRoot;
    private int mType;
    private MultiTypeAdapter mAdapter;

    public static ZhuanlanView newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_TYPE, type);
        ZhuanlanView fragment = new ZhuanlanView();
        fragment.setArguments(args);
        return fragment;
    }

    private void refreshUI() {
        Resources.Theme theme = getActivity().getTheme();
        TypedValue rootViewBackground = new TypedValue();
        TypedValue itemViewBackground = new TypedValue();
        TypedValue textColorPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true);
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true);
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
        mRoot.setBackgroundResource(rootViewBackground.resourceId);

        Resources resources = getResources();
        int childCount = mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CardView cardView = mRecyclerView.getChildAt(i).findViewById(R.id.cardview);
            cardView.setBackgroundResource(itemViewBackground.resourceId);

            TextView tv_name = cardView.findViewById(R.id.tv_name);
            tv_name.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_followersCount = cardView.findViewById(R.id.tv_followersCount);
            tv_followersCount.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_postsCount = cardView.findViewById(R.id.tv_postsCount);
            tv_postsCount.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_intro = cardView.findViewById(R.id.tv_intro);
            tv_intro.setTextColor(resources.getColor(textColorPrimary.resourceId));
        }

        RecyclerViewUtil.invalidateCacheItem(mRecyclerView);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_zhuanlan;
    }

    @Override
    protected void initViews(View view) {
        mRoot = view.findViewById(R.id.root);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        mSwipeRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(ARGUMENT_TYPE);
        }
    }

    @Override
    protected void subscribeUI() {
        DaggerZhuanlanComponent.builder()
                .appComponent(App.sAppComponent)
                .zhuanlanModule(new ZhuanlanModule(this, mType, mApplication))
                .build().inject(this);

//        ZhuanlanViewModel.Factory factory = new ZhuanlanViewModel.Factory(App.sApp, mType);
//        mModel = ViewModelProviders.of(this, factory).get(ZhuanlanViewModel.class);
        mModel.getList().observe(this, new Observer<List<ZhuanlanBean>>() {
            @Override
            public void onChanged(@Nullable List<ZhuanlanBean> list) {
                if (null != list && list.size() > 0) {
                    onSetAdapter(list);
                } else {
                    onShowNetError();
                }
            }
        });
        mModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    onShowLoading();
                } else {
                    onHideLoading();
                }
            }
        });
        mModel.isRefreshUI().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                refreshUI();
            }
        });
    }

    private void onSetAdapter(List<ZhuanlanBean> list) {
        if (mAdapter == null) {
            mAdapter = new MultiTypeAdapter(list);
            mAdapter.register(ZhuanlanBean.class, new ZhuanlanViewBinder());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mModel.handleData();
    }

    private void onShowLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void onHideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void onShowNetError() {
        Snackbar.make(mSwipeRefreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setEnabled(true);
    }
}
