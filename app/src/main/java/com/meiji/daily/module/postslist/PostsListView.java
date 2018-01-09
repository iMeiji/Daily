package com.meiji.daily.module.postslist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meiji.daily.App;
import com.meiji.daily.R;
import com.meiji.daily.bean.FooterBean;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.binder.FooterViewBinder;
import com.meiji.daily.binder.PostsListViewBinder;
import com.meiji.daily.module.base.BaseFragment;
import com.meiji.daily.util.DiffCallback;
import com.meiji.daily.util.OnLoadMoreListener;
import com.meiji.daily.util.SettingHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Meiji on 2017/12/5.
 */

public class PostsListView extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    static final String ARGUMENT_SLUG = "ARGUMENT_SLUG";
    static final String ARGUMENT_NAME = "ARGUMENT_NAME";
    static final String ARGUMENT_POSTSCOUNT = "ARGUMENT_POSTSCOUNT";
    static final String TAG = "PostsListView";

    @Inject
    @Named("title")
    String mTitle;
    @Inject
    PostsListViewModel mModel;
    @Inject
    SettingHelper mSettingHelper;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Items mOldItems = new Items();
    private MultiTypeAdapter mAdapter;
    private boolean mCanloadmore;

    public static PostsListView newInstance(String slug, String title, int postsCount) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_SLUG, slug);
        args.putString(ARGUMENT_NAME, title);
        args.putInt(ARGUMENT_POSTSCOUNT, postsCount);
        PostsListView fragment = new PostsListView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        DaggerPostsListComponent.builder()
                .appComponent(App.Companion.getSAppComponent())
                .postsListModule(new PostsListModule(this))
                .build().inject(this);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_postslist;
    }

    @Override
    protected void initViews(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar_title);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        initToolBar(toolbar, true, mTitle);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        // 设置下拉刷新的按钮的颜色
        mRefreshLayout.setColorSchemeColors(mSettingHelper.getColor());
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MultiTypeAdapter();
        mAdapter.register(PostsListBean.class, new PostsListViewBinder());
        mAdapter.register(FooterBean.class, new FooterViewBinder());
        mAdapter.setItems(mOldItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void subscribeUI() {
        mModel.getListLiveData().observe(this, new Observer<List<PostsListBean>>() {
            @Override
            public void onChanged(@Nullable List<PostsListBean> list) {
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
        mModel.isEnd().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Snackbar.make(mRefreshLayout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
                if (mOldItems.size() > 0) {
                    mOldItems.remove(mOldItems.size() - 1);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onSetAdapter(final List<PostsListBean> list) {
        Items newItems = new Items(list);
        newItems.add(new FooterBean());

        DiffCallback.create(mOldItems, newItems, mAdapter);
        mOldItems.clear();
        mOldItems.addAll(newItems);

        mCanloadmore = true;

        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCanloadmore) {
                    mCanloadmore = false;
                    mModel.loadMore();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mModel.doRefresh();
    }

    public void onShowLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    public void onHideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    public void onShowNetError() {
        Snackbar.make(mRefreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }
}