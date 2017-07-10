package com.meiji.daily.injector.component;

import com.meiji.daily.injector.module.PostsListModule;
import com.meiji.daily.mvp.postslist.PostsListView;

import dagger.Component;

/**
 * Created by Meiji on 2017/7/10.
 */
@Component(modules = PostsListModule.class)
public interface PostsListComponent {

    void inject(PostsListView view);

}
