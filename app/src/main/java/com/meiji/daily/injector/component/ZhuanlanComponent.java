package com.meiji.daily.injector.component;

import com.meiji.daily.injector.module.ZhuanlanModule;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanView;

import dagger.Component;

/**
 * Created by Meiji on 2017/7/10.
 */
@Component(modules = ZhuanlanModule.class)
public interface ZhuanlanComponent {

    void inject(ZhuanlanView view);

}
