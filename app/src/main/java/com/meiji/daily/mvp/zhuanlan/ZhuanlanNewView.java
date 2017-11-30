package com.meiji.daily.mvp.zhuanlan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.RxBus;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.mvp.base.BaseFragment;
import com.meiji.daily.mvp.base.IBasePresenter;
import com.meiji.daily.util.RecyclerViewUtil;
import com.meiji.daily.util.SettingUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Meiji on 2017/11/29.
 */

public class ZhuanlanNewView extends BaseFragment<IBasePresenter> implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ZhuanlanNewView";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout root;
    private int type;
    private Observable<Boolean> observable;
    private ZhuanlanViewModel model;

    public static ZhuanlanNewView newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TAG, type);
        ZhuanlanNewView fragment = new ZhuanlanNewView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observable = RxBus.getInstance().register(Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isNightMode) throws Exception {
                refreshUI();
            }
        });
    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(Boolean.class, observable);
        super.onDestroy();
    }

    private void refreshUI() {
        Log.d(TAG, "refreshUI: ");
        Resources.Theme theme = getActivity().getTheme();
        TypedValue rootViewBackground = new TypedValue();
        TypedValue itemViewBackground = new TypedValue();
        TypedValue textColorPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true);
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true);
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
        root.setBackgroundResource(rootViewBackground.resourceId);

        Resources resources = getResources();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CardView cardView = recyclerView.getChildAt(i).findViewById(R.id.cardview);
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

        RecyclerViewUtil.invalidateCacheItem(recyclerView);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_zhuanlan;
    }

    @Override
    protected void initViews(View view) {
        root = view.findViewById(R.id.root);
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt(TAG);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeUI();
    }

    private void subscribeUI() {
        if (!isAdded()) {
            return;
        }
        ZhuanlanViewModel.Factory factory = new ZhuanlanViewModel.Factory(InitApp.application, type);
        model = ViewModelProviders.of(this, factory).get(ZhuanlanViewModel.class);
        model.getList().observe(this, new Observer<List<ZhuanlanBean>>() {
            @Override
            public void onChanged(@Nullable List<ZhuanlanBean> list) {
                if (null != list && list.size() > 0) {
                    onSetAdapter(list);
                } else {
                    onShowNetError();
                }
            }
        });
        model.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    onShowLoading();
                } else {
                    onHideLoading();
                }
            }
        });
    }

    private void onSetAdapter(List<ZhuanlanBean> list) {
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
        model.getData();
    }

    private void onShowLoading() {
        refreshLayout.setRefreshing(true);
        recyclerView.setVisibility(View.GONE);
    }

    private void onHideLoading() {
        refreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void onShowNetError() {
        Snackbar.make(refreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        refreshLayout.setEnabled(true);
    }

    @Override
    public void initInjector() {
    }
}
