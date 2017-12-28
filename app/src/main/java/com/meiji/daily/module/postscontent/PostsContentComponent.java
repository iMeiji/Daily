package com.meiji.daily.module.postscontent;

import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/28.
 */

@FragmentScoped
@Component(modules = PostsContentModule.class, dependencies = AppComponent.class)
public interface PostsContentComponent {

    void inject(PostsContentView postsContentView);
}
