package com.meiji.daily.mvp.postslist;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.daily.InitApp;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.mvp.postscontent.PostsContentView;

import java.util.List;

import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_SLUG;
import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_TITLE;
import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_TITLEIMAGE;

/**
 * Created by Meiji on 2016/11/19.
 */

class PostsListPresenter implements IPostsList.Presenter {

    private IPostsList.View view;
    private IPostsList.Model model;
    private List<PostsListBean> list;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetAdapter();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    PostsListPresenter(IPostsList.View view) {
        this.view = view;
        this.model = new PostsListModel();
    }

    @Override
    public void doRequestData(final String slug, final int offset) {
        view.onShowRefreshing();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.retrofitRequest(slug, offset);
                if (result) {
                    Message message = handler.obtainMessage(1);
                    message.sendToTarget();
                } else {
                    Message message = handler.obtainMessage(0);
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        model.onDestroy();
        model.clearList();
        view.onRequestData();
    }

    @Override
    public void doSetAdapter() {
        view.onHideRefreshing();
        list = model.getList();
        view.onSetAdapter(list);
    }

    @Override
    public void doOnClickItem(int position) {
        String titleImage = list.get(position).getTitleImage();
        String title = list.get(position).getTitle();
        int slug = list.get(position).getSlug();

        Intent intent = new Intent(InitApp.AppContext, PostsContentView.class);
        intent.putExtra(POSTSLISTBEAN_TITLEIMAGE, titleImage);
        intent.putExtra(POSTSLISTBEAN_TITLE, title);
        intent.putExtra(POSTSLISTBEAN_SLUG, slug);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onFail();
    }
}
