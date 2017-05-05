package com.meiji.daily.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by Meiji on 2017/5/5.
 */

public abstract class BaseFragment extends RxFragment implements IBaseView {

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
