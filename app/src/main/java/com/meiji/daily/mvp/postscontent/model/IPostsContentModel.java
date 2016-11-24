package com.meiji.daily.mvp.postscontent.model;

/**
 * Created by Meiji on 2016/11/22.
 */

public interface IPostsContentModel {

    boolean getRequestData(int slug);

    String getContent();

    void onDestroy();
}
