package com.meiji.daily.module.postslist;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.meiji.daily.di.scope.FragmentScoped;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Meiji on 2017/12/28.
 */

@Module
public class PostsListModule {

    private final PostsListView mPostsListView;

    public PostsListModule(PostsListView postsListView) {
        mPostsListView = postsListView;
    }

    @FragmentScoped
    @Provides
    PostsListViewModel provideModule(@Named("application") Application application,
                                     @Named("slug") String slug, int postCount, Retrofit retrofit) {
        PostsListViewModel.Factory factory =
                new PostsListViewModel.Factory(application, slug, postCount, retrofit);
        return ViewModelProviders.of(mPostsListView, factory).get(PostsListViewModel.class);
    }

    @FragmentScoped
    @Provides
    @Named("slug")
    String provideSlug() {
        Bundle bundle = mPostsListView.getArguments();
        if (bundle != null) {
            return bundle.getString(PostsListView.ARGUMENT_SLUG);
        }
        return "";
    }

    @FragmentScoped
    @Provides
    int providePostCount() {
        Bundle bundle = mPostsListView.getArguments();
        if (bundle != null) {
            return bundle.getInt(PostsListView.ARGUMENT_POSTSCOUNT, 0);
        }
        return 0;
    }

    @FragmentScoped
    @Provides
    @Named("title")
    String provideTitle() {
        Bundle bundle = mPostsListView.getArguments();
        if (bundle != null) {
            return bundle.getString(PostsListView.ARGUMENT_NAME);
        }
        return "";
    }
}
