package com.meiji.daily.binder

import com.meiji.daily.di.component.AppComponent
import com.meiji.daily.di.scope.FragmentScoped

import dagger.Component

/**
 * Created by Meiji on 2017/12/28.
 */
@FragmentScoped
@Component(dependencies = arrayOf(AppComponent::class))
internal interface ItemViewComponent {

    fun inject(footerViewBinder: FooterViewBinder)

    fun inject(zhuanlanViewBinder: ZhuanlanViewBinder)

    fun inject(postsListViewBinder: PostsListViewBinder)
}
