package com.meiji.daily.module.zhuanlan;

import com.meiji.daily.di.component.AppComponent;
import com.meiji.daily.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by Meiji on 2017/12/21.
 */
@FragmentScoped
@Component(modules = ZhuanlanModule.class, dependencies = AppComponent.class)
public interface ZhuanlanComponent {

    // 与 dependencies 有冲突
//    @Component.Builder
//    interface Builder {
//        Builder injectView(ZhuanlanView view);
//
//        Builder injectType(int type);
//
//        ZhuanlanComponent build();
//    }

    void inject(ZhuanlanView view);
}
