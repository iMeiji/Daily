package com.meiji.daily.mvp.postslist.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.meiji.daily.mvp.postscontent.PostsContentViewImpl;
import com.meiji.daily.mvp.postslist.model.IPostsListModel;
import com.meiji.daily.mvp.postslist.model.PostsListBean;
import com.meiji.daily.mvp.postslist.model.PostsListModelImpl;
import com.meiji.daily.mvp.postslist.view.IPostsListView;

import java.util.List;

import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_SLUG;
import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_TITLE;
import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_TITLEIMAGE;

/**
 * Created by Meiji on 2016/11/19.
 */

public class PostsListPresenterImpl implements IPostsListPresenter {

    private Context mContext;
    private IPostsListView view;
    private IPostsListModel model;
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
    private int postCount;

    public PostsListPresenterImpl(IPostsListView view, Context mContext) {
        this.view = view;
        this.mContext = mContext;
        this.model = new PostsListModelImpl();
    }

    /**
     * 请求数据
     */
    @Override
    public void doRequestData(final String url) {
        view.onShowRefreshing();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean request = model.getRequestData(url);
                if (request) {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 1;
                    message.sendToTarget();
                } else {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 0;
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

    /**
     * 设置适配器
     */
    @Override
    public void doSetAdapter() {
        view.onHideRefreshing();
        list = model.getList();
        view.onSetAdapter(list);
    }

    /**
     * 获取点击栏信息
     *
     * @param position
     */
    @Override
    public void doOnClickItem(int position) {
        String titleImage = list.get(position).getTitleImage();
        String title = list.get(position).getTitle();
        int slug = list.get(position).getSlug();

        Intent intent = new Intent(mContext, PostsContentViewImpl.class);
        intent.putExtra(POSTSLISTBEAN_TITLEIMAGE, titleImage);
        intent.putExtra(POSTSLISTBEAN_TITLE, title);
        intent.putExtra(POSTSLISTBEAN_SLUG, slug);
        Log.d(this.toString(), titleImage + title + slug);
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
