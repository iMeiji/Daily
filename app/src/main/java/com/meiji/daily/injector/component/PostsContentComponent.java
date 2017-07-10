package com.meiji.daily.injector.component;

import com.meiji.daily.injector.module.PostsContentModule;
import com.meiji.daily.mvp.postscontent.PostsContentView;

import dagger.Component;

/**
 * Created by Meiji on 2017/7/10.
 */
@Component(modules = PostsContentModule.class)
public interface PostsContentComponent {

    void inject(PostsContentView view);

}
