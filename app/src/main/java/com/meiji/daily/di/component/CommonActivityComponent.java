package com.meiji.daily.di.component;

import com.meiji.daily.MainActivity;
import com.meiji.daily.di.scope.ActivityScoped;
import com.meiji.daily.module.base.BaseActivity;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/28.
 */

@ActivityScoped
@Component(dependencies = AppComponent.class)
public interface CommonActivityComponent {

    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);
}
