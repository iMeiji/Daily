package com.meiji.daily.binder;

import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/28.
 */
@FragmentScoped
@Component(dependencies = AppComponent.class)
public interface ItemViewComponent {

    void inject(FooterViewBinder footerViewBinder);

    void inject(ZhuanlanViewBinder zhuanlanViewBinder);

    void inject(PostsListViewBinder postsListViewBinder);
}
