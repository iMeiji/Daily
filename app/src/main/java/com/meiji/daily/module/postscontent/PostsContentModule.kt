package com.meiji.daily.module.postscontent

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
class PostsContentModule(private val mPostsContentView: PostsContentView) {

    @FragmentScoped
    @Provides
    internal fun provideModel(@Named("application") application: Application,
                              @Named("slug") slug: String,
                              retrofit: Retrofit): PostsContentViewModel {
        val factory = PostsContentViewModel.Factory(application, slug, retrofit)
        return ViewModelProviders.of(mPostsContentView, factory).get(PostsContentViewModel::class.java)
    }

    @Provides
    @FragmentScoped
    @Named("image")
    internal fun provideImage(): String {
        val bundle = mPostsContentView.arguments
        return if (bundle != null) {
            bundle.getString(PostsContentView.ARGUMENT_TITLEIMAGE)
        } else ""
    }

    @Provides
    @FragmentScoped
    @Named("title")
    internal fun provideTitle(): String {
        val bundle = mPostsContentView.arguments
        return if (bundle != null) {
            bundle.getString(PostsContentView.ARGUMENT_TITLE)
        } else ""
    }

    @Provides
    @FragmentScoped
    @Named("slug")
    internal fun provideSlug(): String {
        val bundle = mPostsContentView.arguments
        return bundle?.getString(PostsContentView.ARGUMENT_SLUG, "") ?: ""
    }
}
