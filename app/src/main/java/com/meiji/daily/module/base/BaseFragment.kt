package com.meiji.daily.module.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Meiji on 2017/12/4.
 */

abstract class BaseFragment : Fragment() {

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract fun attachLayoutId(): Int

    /**
     * 初始化视图控件
     */
    protected abstract fun initViews(view: View)

    /**
     * 初始化数据
     */
    protected fun initData() {}

    /**
     * 订阅UI组件
     */
    protected open fun subscribeUI() {}

    /**
     * 初始化 Dagger
     */
    protected open fun initInject() {}

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected fun initToolBar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        if (activity != null) {
            (activity as BaseActivity).initToolBar(toolbar, homeAsUpEnabled, title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(attachLayoutId(), container, false)
        initViews(view)
        initData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isAdded) {
            return
        }
        subscribeUI()
    }
}
