package com.meiji.daily.module.useradd;

import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/28.
 */
@FragmentScoped
@Component(modules = UserAddModule.class, dependencies = AppComponent.class)
public interface UserAddComponent {

    void inject(UserAddView userAddView);
}
