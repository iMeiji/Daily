package com.meiji.daily.mvp.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.CircleView;
import com.meiji.daily.utils.ColorUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Meiji on 2016/11/27.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {

    @Override
    protected void onResume() {
        super.onResume();
        int color = ColorUtils.getColor();
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

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
