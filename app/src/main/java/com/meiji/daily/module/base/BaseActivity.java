package com.meiji.daily.module.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.CircleView;
import com.meiji.daily.App;
import com.meiji.daily.R;
import com.meiji.daily.util.SettingHelper;

import javax.inject.Inject;

/**
 * Created by Meiji on 2017/12/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected SettingHelper mSettingHelper;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    protected void initTheme() {
        boolean isNightMode = mSettingHelper.getIsNightMode();
        if (isNightMode) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DaggerBaseActivityComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this);
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(attachLayoutId());
        initViews();
        initData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int color = mSettingHelper.getColor();
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
            getWindow().setNavigationBarColor(color);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
