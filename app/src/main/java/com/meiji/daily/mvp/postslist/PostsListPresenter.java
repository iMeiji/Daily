package com.meiji.daily.mvp.postslist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

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

    private Context mContext;
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

    PostsListPresenter(IPostsList.View view, Context mContext) {
        this.view = view;
        this.mContext = mContext;
        this.model = new PostsListModel();
    }

    @Override
    public void doRequestData(final String url) {
        view.onShowRefreshing();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(url);
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

        Intent intent = new Intent(mContext, PostsContentView.class);
        intent.putExtra(POSTSLISTBEAN_TITLEIMAGE, titleImage);
        intent.putExtra(POSTSLISTBEAN_TITLE, title);
        intent.putExtra(POSTSLISTBEAN_SLUG, slug);
        mContext.startActivity(intent);
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
