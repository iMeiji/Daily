package com.meiji.daily.module.base;

import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.scope.ActivityScoped;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/28.
 */

@ActivityScoped
@Component(dependencies = AppComponent.class)
public interface BaseActivityComponent {

    void inject(BaseActivity baseActivity);
}
