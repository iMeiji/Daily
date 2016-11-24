package com.meiji.daily.mvp.postscontent.presenter;

import android.os.Handler;
import android.os.Message;

import com.meiji.daily.mvp.postscontent.model.IPostsContentModel;
import com.meiji.daily.mvp.postscontent.model.PostsContentModelImpl;
import com.meiji.daily.mvp.postscontent.view.IPostsContentView;

/**
 * Created by Meiji on 2016/11/23.
 */

public class PostsContentPresenterImpl implements IPostsContentPresenter {

    private IPostsContentView view;
    private IPostsContentModel model;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                view.onSetWebView(model.getContent());
            }
            if (message.what == 2) {
                onFail();
            }
            return false;
        }
    });

    public PostsContentPresenterImpl(IPostsContentView view) {
        this.view = view;
        this.model = new PostsContentModelImpl();
    }

    @Override
    public void doRequestData(final int slug) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(slug);
                System.out.println(result);
                if (result) {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 1;
                    message.sendToTarget();
                } else {
                    Message message = new Message();
                    message = handler.obtainMessage();
                    message.what = 2;
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void onFail() {
        view.onFail();
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }
}
