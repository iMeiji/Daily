package com.meiji.daily.module.base

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.afollestad.materialdialogs.color.CircleView
import com.meiji.daily.App
import com.meiji.daily.R
import com.meiji.daily.di.component.DaggerCommonActivityComponent
import com.meiji.daily.util.SettingHelper

import javax.inject.Inject

/**
 * Created by Meiji on 2017/12/5.
 */

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var mSettingHelper: SettingHelper

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract fun attachLayoutId(): Int

    /**
     * 初始化视图控件
     */
    protected abstract fun initViews()

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected open fun initData(savedInstanceState: Bundle?) {}

    protected fun initTheme() {
        val isNightMode = mSettingHelper.isNightMode
        if (isNightMode) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    fun initToolBar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String?) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCommonActivityComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this)
        super.onCreate(savedInstanceState)
        initTheme()
        setContentView(attachLayoutId())
        initViews()
        initData(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val color = mSettingHelper.color
        if (supportActionBar != null)
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(color))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = CircleView.shiftColorDown(color)
            window.navigationBarColor = color
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
