package com.meiji.daily.mvp.postslist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.meiji.daily.R;
import com.meiji.daily.adapter.PostsListAdapter;
import com.meiji.daily.mvp.postslist.model.PostsListBean;
import com.meiji.daily.mvp.postslist.presenter.IPostsListPresenter;
import com.meiji.daily.mvp.postslist.presenter.PostsListPresenterImpl;
import com.meiji.daily.mvp.postslist.view.IPostsListView;
import com.meiji.daily.utils.Api;

import java.util.List;

import static com.meiji.daily.mvp.zhuanlan.model.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.mvp.zhuanlan.model.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.mvp.zhuanlan.model.ZhuanlanBean.ZHUANLANBEAN_SLUG;

/**
 * Created by Meiji on 2016/11/18.
 */

public class PostsListViewImpl extends AppCompatActivity implements IPostsListView, SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private RecyclerView rv_posts;
    private SwipeRefreshLayout refresh;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private IPostsListPresenter presenter;
    private String url;
    private PostsListAdapter adapter;
    private int postCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postlist);
        initView();
        initData();
        onShowRefreshing();
        onRequestData();
    }

    private void initData() {
        Intent intent = getIntent();
        String slug = intent.getStringExtra(ZHUANLANBEAN_SLUG);
        postCount = intent.getIntExtra(ZHUANLANBEAN_POSTSCOUNT, 0);
        String title = intent.getStringExtra(ZHUANLANBEAN_NAME);
        getSupportActionBar().setTitle(title);
        url = Api.BASE_URL + slug + "/posts?limit=5";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 请求数据
     */
    @Override
    public void onRequestData() {

        presenter.doRequestData(url);
    }

    /**
     * 设置适配器
     *
     * @param list
     */
    @Override
    public void onSetAdapter(List<PostsListBean> list) {
        onHideRefreshing();
        if (adapter == null) {
            adapter = new PostsListAdapter(list, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyItemInserted(list.size());
        }
        // 列表文章 < 总文章 继续加载 这里要判断recyclerview是否滚动到底在执行 不然后台一直加载
        if (list.size() < postCount) {
            presenter.doRequestData(url + "&offset=" + list.size());
        }
    }

    /**
     * 正在刷新
     */
    @Override
    public void onShowRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    /**
     * 完成刷新
     */
    @Override
    public void onHideRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    /**
     * 请求数据失败
     */
    @Override
    public void onFail() {
        onHideRefreshing();
        Snackbar.make(refreshLayout, "网络不给力", Snackbar.LENGTH_SHORT).show();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_postlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        recyclerView = (RecyclerView) findViewById(R.id.rv_posts);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setOnRefreshListener(this);
        presenter = new PostsListPresenterImpl(this, this);
    }

    @Override
    public void onRefresh() {
        onShowRefreshing();
        presenter.doRefresh();
        onRequestData();
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}