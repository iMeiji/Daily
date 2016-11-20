package com.meiji.daily.mvp.postslist.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.meiji.daily.mvp.postslist.model.IPostsListModel;
import com.meiji.daily.mvp.postslist.model.PostsListModelImpl;
import com.meiji.daily.mvp.postslist.view.IPostsListView;

/**
 * Created by Meiji on 2016/11/19.
 */

public class PostsListPresenterImpl implements IPostsListPresenter {

    private Context mContext;
    private IPostsListView view;
    private IPostsListModel model;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetAdapter();
            }
            if (message.what == 0) {
                view.onFail();
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
        model.clearList();
    }

    /**
     * 设置适配器
     */
    @Override
    public void doSetAdapter() {
        view.onSetAdapter(model.getList());
    }

    /**
     * 获取点击栏信息
     *
     * @param position
     */
    @Override
    public void doOnClickItem(int position) {

    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }
}
