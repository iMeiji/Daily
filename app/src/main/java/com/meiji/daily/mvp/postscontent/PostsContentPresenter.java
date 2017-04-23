package com.meiji.daily.mvp.postscontent;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Meiji on 2016/11/23.
 */

class PostsContentPresenter implements IPostsContent.Presenter {

    private IPostsContent.View view;
    private IPostsContent.Model model;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSetWebView();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    PostsContentPresenter(IPostsContent.View view) {
        this.view = view;
        this.model = new PostsContentModel();
    }

    @Override
    public void doRequestData(final int slug) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.retrofitRequest(slug);
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
    public void doSetWebView() {
        view.onSetWebView(model.getContent());
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
