package com.meiji.daily;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.meiji.daily.di.component.DaggerCommonActivityComponent;
import com.meiji.daily.module.base.BaseActivity;
import com.meiji.daily.module.useradd.UserAddView;
import com.meiji.daily.module.zhuanlan.ZhuanlanView;
import com.meiji.daily.util.RxBusHelper;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {

    static final String TAG = "MainActivity";
    @Inject
    RxBusHelper mRxBusHelper;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private SwitchCompat mSwitchCompat;
    private long mExitTime;
    private Flowable<Boolean> mRxBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DaggerCommonActivityComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this);
        super.onCreate(savedInstanceState);
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI);
        mRxBus.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isNightMode) throws Exception {
                showAnimation();
                refreshUI();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus);
        super.onDestroy();
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        replaceFragment(Constant.TYPE_PRODUCT);
        mNavigationView.setCheckedItem(R.id.nav_product);
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar, false, null);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mSwitchCompat = mNavigationView.getMenu().findItem(R.id.app_bar_switch).getActionView().findViewById(R.id.switch_input);
        mSwitchCompat.setChecked(mSettingHelper.getIsNightMode());

        setUpSwitch();

        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isNightMode) {
                mSettingHelper.setIsNightMode(isNightMode);
                setUpSwitch();
                if (isNightMode) {
                    setTheme(R.style.DarkTheme);
                } else {
                    setTheme(R.style.LightTheme);
                }
                mRxBusHelper.post(Constant.RxBusEvent.REFRESHUI, isNightMode);
            }
        });
    }

    private void setUpSwitch() {
        boolean isNightMode = mSettingHelper.getIsNightMode();
        if (isNightMode) {
            mSwitchCompat.setThumbTintList(ColorStateList.valueOf(mSettingHelper.getColor()));
        } else {
            Resources.Theme theme = getTheme();
            Resources resources = getResources();
            TypedValue textColorPrimary = new TypedValue();
            theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
            mSwitchCompat.setThumbTintList(resources.getColorStateList(textColorPrimary.resourceId));
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if ((currentTime - mExitTime) < 2000) {
            super.onBackPressed();
        } else {
            Snackbar.make(mDrawerLayout, getString(R.string.double_click_exit), Snackbar.LENGTH_SHORT).show();
            mExitTime = currentTime;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product) {
            replaceFragment(Constant.TYPE_PRODUCT);

        } else if (id == R.id.nav_life) {
            replaceFragment(Constant.TYPE_LIFE);

        } else if (id == R.id.nav_music) {
            replaceFragment(Constant.TYPE_MUSIC);

        } else if (id == R.id.nav_emotion) {
            replaceFragment(Constant.TYPE_EMOTION);

        } else if (id == R.id.nav_profession) {
            replaceFragment(Constant.TYPE_FINANCE);

        } else if (id == R.id.nav_zhihu) {
            replaceFragment(Constant.TYPE_ZHIHU);

        } else if (id == R.id.nav_user_add) {
            replaceFragment(Constant.TYPE_USERADD);

        } else if (id == R.id.nav_color_chooser) {
            createColorChooserDialog();

        } else if (id == R.id.nav_about) {
            AboutActivity.start(this);

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void refreshUI() {
        Resources.Theme theme = getTheme();
        TypedValue rootViewBackground = new TypedValue();
        TypedValue textColorPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true);
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
        Resources resources = getResources();
        mNavigationView.setBackgroundResource(rootViewBackground.resourceId);
        mNavigationView.setItemBackgroundResource(rootViewBackground.resourceId);
        mNavigationView.setItemTextColor(resources.getColorStateList(textColorPrimary.resourceId));
        mNavigationView.setItemIconTintList(resources.getColorStateList(textColorPrimary.resourceId));
    }

    private void replaceFragment(int type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragmentById = fragmentManager.findFragmentByTag(String.valueOf(type));
        if (fragmentById == null) {
            Fragment fragment;
            if (type != Constant.TYPE_USERADD) {
                fragment = ZhuanlanView.newInstance(type);
            } else {
                fragment = new UserAddView();
            }
            fragmentTransaction
                    .add(R.id.content_main, fragment, String.valueOf(type));
        } else {
            for (Fragment fragment : fragmentManager.getFragments()) {
                fragmentTransaction.hide(fragment);
            }
            fragmentTransaction.show(fragmentById);
        }
        fragmentTransaction.commit();
    }

    private void createColorChooserDialog() {
        new ColorChooserDialog.Builder(this, R.string.md_color_chooser_title)
                .doneButton(R.string.md_done)
                .cancelButton(R.string.md_cancel)
                .backButton(R.string.md_back)
                .allowUserColorInput(true)
                .allowUserColorInputAlpha(false)
                .theme(mSettingHelper.getIsNightMode() ? Theme.DARK : Theme.LIGHT)
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
            mSettingHelper.setColor(selectedColor);
        }
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        setUpSwitch();
    }

    private void showAnimation() {
        final View decorview = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorview);
        if (decorview instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackground(new BitmapDrawable((getResources()), cacheBitmap));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorview).addView(view, layoutParams);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorview).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnable = true;
        view.setDrawingCacheEnabled(drawingCacheEnable);
        view.buildDrawingCache(drawingCacheEnable);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
