package com.meiji.daily.injector.module;

import com.meiji.daily.module.useradd.IUserAdd;
import com.meiji.daily.module.useradd.UserAddPresenter;
import com.meiji.daily.module.useradd.UserAddView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/7/11.
 */
@Module
public class UserAddModule {

    private final UserAddView view;

    public UserAddModule(UserAddView view) {
        this.view = view;
    }

    @Provides
    public IUserAdd.Presenter providePresenter() {
        return new UserAddPresenter(view);
    }
}
