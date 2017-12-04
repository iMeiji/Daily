package com.meiji.daily.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Meiji on 2017/12/4.
 */

public abstract class BaseNewFragment extends Fragment {

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 订阅UI组件
     */
    protected abstract void subscribeUI();

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        initViews(view);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isAdded()) {
            return;
        }
        subscribeUI();
    }
}
