package com.meiji.daily;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.meiji.daily.mvp.useradd.UseraddView;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanModel;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanView;
import com.meiji.daily.utils.ColorUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private long exitTime;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        replaceFragment(ZhuanlanModel.TYPE_PRODUCT);
        navigationView.setCheckedItem(R.id.nav_product);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Snackbar.make(drawerLayout, getString(R.string.double_click_exit), Snackbar.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product) {
            replaceFragment(ZhuanlanModel.TYPE_PRODUCT);

        } else if (id == R.id.nav_life) {
            replaceFragment(ZhuanlanModel.TYPE_LIFE);

        } else if (id == R.id.nav_music) {
            replaceFragment(ZhuanlanModel.TYPE_MUSIC);

        } else if (id == R.id.nav_emotion) {
            replaceFragment(ZhuanlanModel.TYPE_EMOTION);

        } else if (id == R.id.nav_profession) {
            replaceFragment(ZhuanlanModel.TYPE_FINANCE);

        } else if (id == R.id.nav_zhihu) {
            replaceFragment(ZhuanlanModel.TYPE_ZHIHU);

        } else if (id == R.id.nav_user_add) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new UseraddView()).commit();

        } else if (id == R.id.nav_color_chooser) {
            createColorChooserDialog();

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(int type) {
        ZhuanlanView fragment = ZhuanlanView.newInstance(type);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void createColorChooserDialog() {
        new ColorChooserDialog.Builder(this, R.string.md_color_chooser_title)
                .doneButton(R.string.md_done)
                .cancelButton(R.string.md_cancel)
                .allowUserColorInput(true)
                .customButton(R.string.md_custom)
                .show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(selectedColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(selectedColor));
            getWindow().setNavigationBarColor(selectedColor);
        }
        if (!dialog.isAccentMode()) {
            ColorUtils.setColor(selectedColor);
        }
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
