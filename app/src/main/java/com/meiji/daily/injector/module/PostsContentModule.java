package com.meiji.daily.injector.module;

import com.meiji.daily.mvp.postscontent.IPostsContent;
import com.meiji.daily.mvp.postscontent.PostsContentPresenter;
import com.meiji.daily.mvp.postscontent.PostsContentView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/7/11.
 */
@Module
public class PostsContentModule {

    private final PostsContentView view;

    public PostsContentModule(PostsContentView view) {
        this.view = view;
    }

    @Provides
    public IPostsContent.Presenter providePresenter() {
        return new PostsContentPresenter(view);
    }
}
