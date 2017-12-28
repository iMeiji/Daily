package com.meiji.daily.module.postscontent;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.meiji.daily.di.scope.FragmentScoped;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/12/28.
 */

@Module
public class PostsContentModule {

    private final PostsContentView mPostsContentView;

    public PostsContentModule(PostsContentView postsContentView) {
        mPostsContentView = postsContentView;
    }

    @FragmentScoped
    @Provides
    PostsContentViewModel provideModel(@Named("application") Application application, @Named("slug") int slug) {
        PostsContentViewModel.Factory factory = new PostsContentViewModel.Factory(application, slug);
        return ViewModelProviders.of(mPostsContentView, factory).get(PostsContentViewModel.class);
    }

    @Provides
    @FragmentScoped
    @Named("image")
    String provideImage() {
        Bundle bundle = mPostsContentView.getArguments();
        if (bundle != null) {
            return bundle.getString(PostsContentView.ARGUMENT_TITLEIMAGE);
        }
        return "";
    }

    @Provides
    @FragmentScoped
    @Named("title")
    String provideTitle() {
        Bundle bundle = mPostsContentView.getArguments();
        if (bundle != null) {
            return bundle.getString(PostsContentView.ARGUMENT_TITLE);
        }
        return "";
    }

    @Provides
    @FragmentScoped
    @Named("slug")
    int provideSlug() {
        Bundle bundle = mPostsContentView.getArguments();
        if (bundle != null) {
            return bundle.getInt(PostsContentView.ARGUMENT_SLUG, 0);
        }
        return 0;
    }
}
