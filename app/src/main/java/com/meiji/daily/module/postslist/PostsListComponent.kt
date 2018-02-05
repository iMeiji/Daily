package com.meiji.daily.module.postslist

import com.meiji.daily.di.component.AppComponent
import com.meiji.daily.di.scope.FragmentScoped

import dagger.Component

/**
 * Created by Meiji on 2017/12/28.
 */
@FragmentScoped
@Component(modules = [(PostsListModule::class)], dependencies = [(AppComponent::class)])
interface PostsListComponent {

    fun inject(postsListView: PostsListView)
}
