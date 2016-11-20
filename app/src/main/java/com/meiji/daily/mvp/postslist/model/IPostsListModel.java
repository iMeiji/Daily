package com.meiji.daily.mvp.postslist.model;

import java.util.List;

/**
 * Created by Meiji on 2016/11/18.
 */

public interface IPostsListModel {

    boolean getRequestData(String url);

    List<PostsListBean> getList();

    void clearList();

    void onDestroy();
}
