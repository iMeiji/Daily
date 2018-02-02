package com.meiji.daily.module.postslist

import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import com.meiji.daily.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by Meiji on 2017/12/28.
 */

@Module
class PostsListModule(private val mPostsListView: PostsListView) {

    @FragmentScoped
    @Provides
    internal fun provideModule(@Named("application") application: Application,
                               @Named("slug") slug: String,
                               postCount: Int,
                               retrofit: Retrofit): PostsListViewModel {
        val factory = PostsListViewModel.Factory(application, slug, postCount, retrofit)
        return ViewModelProviders.of(mPostsListView, factory).get(PostsListViewModel::class.java)
    }

    @FragmentScoped
    @Provides
    @Named("slug")
    internal fun provideSlug(): String {
        val bundle = mPostsListView.arguments
        return if (bundle != null) {
            bundle.getString(PostsListView.ARGUMENT_SLUG)
        } else ""
    }

    @FragmentScoped
    @Provides
    internal fun providePostCount(): Int {
        val bundle = mPostsListView.arguments
        return bundle?.getInt(PostsListView.ARGUMENT_POSTSCOUNT, 0) ?: 0
    }

    @FragmentScoped
    @Provides
    @Named("title")
    internal fun provideTitle(): String {
        val bundle = mPostsListView.arguments
        return if (bundle != null) {
            bundle.getString(PostsListView.ARGUMENT_NAME)
        } else ""
    }
}
