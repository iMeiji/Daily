package com.meiji.daily.mvp.postscontent.presenter;

/**
 * Created by Meiji on 2016/11/22.
 */

public interface IPostsContentPresenter {

    void doRequestData(int slug);

    void onFail();

    void onDestroy();
}
