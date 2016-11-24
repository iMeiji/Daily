package com.meiji.daily.mvp.postscontent;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Meiji on 2016/11/23.
 */

public class PostsContentPresenter implements IPostsContent.Presenter {

    private IPostsContent.View view;
    private IPostsContent.Model model;
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

    public PostsContentPresenter(IPostsContent.View view) {
        this.view = view;
        this.model = new PostsContentModel();
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
