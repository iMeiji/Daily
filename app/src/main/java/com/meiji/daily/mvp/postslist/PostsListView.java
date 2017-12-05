package com.meiji.daily.mvp.postslist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.bean.FooterBean;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.binder.FooterViewBinder;
import com.meiji.daily.binder.PostsListViewBinder;
import com.meiji.daily.injector.component.DaggerPostsListComponent;
import com.meiji.daily.injector.module.PostsListModule;
import com.meiji.daily.mvp.base.BaseActivity;
import com.meiji.daily.util.DiffCallback;
import com.meiji.daily.util.OnLoadMoreListener;
import com.meiji.daily.util.SettingUtil;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.meiji.daily.Constant.ZHUANLANBEAN_NAME;
import static com.meiji.daily.Constant.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.Constant.ZHUANLANBEAN_SLUG;


/**
 * Created by Meiji on 2016/11/18.
 */
@Deprecated
public class PostsListView extends BaseActivity<IPostsList.Presenter> implements IPostsList.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PostsListView";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int postCount;
    private String slug;
    private Items oldItems = new Items();

    public static void launch(String slug, String name, int postsCount) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, PostsListView.class)
                .putExtra(ZHUANLANBEAN_SLUG, slug)
                .putExtra(ZHUANLANBEAN_NAME, name)
                .putExtra(ZHUANLANBEAN_POSTSCOUNT, postsCount)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_postslist;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        slug = intent.getStringExtra(ZHUANLANBEAN_SLUG);
        postCount = intent.getIntExtra(ZHUANLANBEAN_POSTSCOUNT, 0);
        String title = intent.getStringExtra(ZHUANLANBEAN_NAME);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        onRequestData();
    }

    @Override
    protected void initInjector() {
        DaggerPostsListComponent.builder()
                .postsListModule(new PostsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onRequestData() {
        onShowLoading();
        presenter.doRequestData(slug, 0);
    }

    @Override
    public void onSetAdapter(final List<PostsListBean> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Items newItems = new Items(list);
                newItems.add(new FooterBean());

                DiffCallback.create(oldItems, newItems, DiffCallback.POSTSLIST, adapter);
                oldItems.clear();
                oldItems.addAll(newItems);
            }
        }).start();

        canLoadMore = true;

        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 列表文章 < 总文章 继续加载 这里要判断recyclerview是否滚动到底再执行 不然后台一直加载
                if ((list.size() < postCount) && canLoadMore) {
                    canLoadMore = false;
                    presenter.doRequestData(slug, list.size());
                } else if ((list.size() == postCount)) {
                    Snackbar.make(refreshLayout, R.string.no_more, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_title);
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refresh_layout);
        initToolBar(toolbar, true, null);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        // 设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        refreshLayout.setOnRefreshListener(this);

        adapter = new MultiTypeAdapter();
        adapter.register(PostsListBean.class, new PostsListViewBinder());
        adapter.register(FooterBean.class, new FooterViewBinder());
        adapter.setItems(oldItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onShowLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onHideLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(refreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }
}