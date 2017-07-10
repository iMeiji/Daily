package com.meiji.daily.injector.module;

import com.meiji.daily.mvp.postslist.IPostsList;
import com.meiji.daily.mvp.postslist.PostsListPresenter;
import com.meiji.daily.mvp.postslist.PostsListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/7/11.
 */
@Module
public class PostsListModule {

    private final PostsListView view;

    public PostsListModule(PostsListView view) {
        this.view = view;
    }

    @Provides
    public IPostsList.Presenter providePresenter() {
        return new PostsListPresenter(view);
    }
}
