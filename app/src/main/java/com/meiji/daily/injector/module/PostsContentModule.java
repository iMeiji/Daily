package com.meiji.daily.injector.module;

import com.meiji.daily.module.postscontent.IPostsContent;
import com.meiji.daily.module.postscontent.PostsContentPresenter;
import com.meiji.daily.module.postscontent.PostsContentView;

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
