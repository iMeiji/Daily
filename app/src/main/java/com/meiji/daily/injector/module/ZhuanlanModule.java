package com.meiji.daily.injector.module;

import com.meiji.daily.mvp.zhuanlan.IZhuanlan;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanPresenter;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Meiji on 2017/7/10.
 */
@Module
public class ZhuanlanModule {

    private final ZhuanlanView view;
    private final int type;

    public ZhuanlanModule(ZhuanlanView view, int type) {
        this.view = view;
        this.type = type;
    }

    @Provides
    public IZhuanlan.Presenter providePresenter() {
        return new ZhuanlanPresenter(view, type);
    }
}
