package com.meiji.daily;

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
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.meiji.daily.mvp.useradd.UseraddView;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanModel;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanView;
import com.meiji.daily.utils.ColorUtil;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

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
            Snackbar.make(drawerLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_copyright) {
            MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.action_copyright)
                    .content(R.string.copyright_content)
                    .neutralText(R.string.got_it)
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).build();

            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.app_bar_main, new UseraddView()).commit();

        } else if (id == R.id.nav_color_chooser) {
            createColorChooserDialog();

        } else if (id == R.id.nav_about) {
            createLicenseDialog();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(int type) {
        ZhuanlanView fragment = ZhuanlanView.newInstance(type);
        getSupportFragmentManager().beginTransaction().replace(R.id.app_bar_main, fragment).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void createColorChooserDialog() {
        new ColorChooserDialog.Builder(this, R.string.color_chooser_title)
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
            ColorUtil.setColor(this, selectedColor);
        }
    }

    private void createLicenseDialog() {
        Notices notices = new Notices();
        notices.addNotice(new Notice("material-dialogs", "https://github.com/afollestad/material-dialogs", "Copyright (c) 2014-2016 Aidan Michael Follestad", new MITLicense()));
        notices.addNotice(new Notice("okhttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "Copyright 2014 - 2016 Henning Dodenhof", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }
}
