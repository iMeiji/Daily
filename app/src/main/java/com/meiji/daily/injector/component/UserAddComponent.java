package com.meiji.daily.injector.component;

import com.meiji.daily.injector.module.UserAddModule;
import com.meiji.daily.mvp.useradd.UserAddView;

import dagger.Component;

/**
 * Created by Meiji on 2017/7/10.
 */
@Component(modules = UserAddModule.class)
public interface UserAddComponent {

    void inject(UserAddView view);

}
