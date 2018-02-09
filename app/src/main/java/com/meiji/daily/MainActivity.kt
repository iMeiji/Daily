package com.meiji.daily

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SwitchCompat
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.color.CircleView
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.meiji.daily.di.component.DaggerCommonActivityComponent
import com.meiji.daily.module.base.BaseActivity
import com.meiji.daily.module.useradd.UserAddView
import com.meiji.daily.module.zhuanlan.ZhuanlanView
import com.meiji.daily.util.RxBusHelper
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_switch.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {
    @Inject
    lateinit var mRxBusHelper: RxBusHelper
    private lateinit var mSwitchCompat: SwitchCompat
    private var mExitTime: Long = 0
    private var mRxBus: Flowable<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCommonActivityComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this)
        super.onCreate(savedInstanceState)
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI)
        mRxBus?.subscribe {
            showAnimation()
            refreshUI()
        }
    }

    override fun onDestroy() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus!!)
        super.onDestroy()
    }

    override fun attachLayoutId() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        replaceFragment(Constant.TYPE_PRODUCT)
        nav_view.setCheckedItem(R.id.nav_product)
    }

    override fun initViews() {
        initToolBar(toolbar, false, null)

        ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close).let {
            drawer_layout.addDrawerListener(it)
            it.syncState()
        }

        nav_view.setNavigationItemSelectedListener(this)

        mSwitchCompat = nav_view.menu.findItem(R.id.app_bar_switch).actionView.switch_input
        mSwitchCompat.isChecked = mSettingHelper.isNightMode

        setUpSwitch()

        mSwitchCompat.setOnCheckedChangeListener { compoundButton, isNightMode ->
            mSettingHelper.isNightMode = isNightMode
            setUpSwitch()
            when {
                isNightMode -> setTheme(R.style.DarkTheme)
                else -> setTheme(R.style.LightTheme)
            }
            mRxBusHelper.post(Constant.RxBusEvent.REFRESHUI, isNightMode)
        }
    }


    @Suppress("deprecation")
    private fun setUpSwitch() {
        val isNightMode = mSettingHelper.isNightMode
        if (isNightMode) {
            mSwitchCompat.thumbTintList = ColorStateList.valueOf(mSettingHelper.color)
        } else {
            val textColorPrimary = TypedValue()
            theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
            mSwitchCompat.thumbTintList = resources.getColorStateList(textColorPrimary.resourceId)
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            (currentTime - mExitTime < 2000) -> super.onBackPressed()
            else -> {
                Snackbar.make(drawer_layout, getString(R.string.double_click_exit), Snackbar.LENGTH_SHORT).show()
                mExitTime = currentTime
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_product -> replaceFragment(Constant.TYPE_PRODUCT)
            R.id.nav_life -> replaceFragment(Constant.TYPE_LIFE)
            R.id.nav_music -> replaceFragment(Constant.TYPE_MUSIC)
            R.id.nav_emotion -> replaceFragment(Constant.TYPE_EMOTION)
            R.id.nav_profession -> replaceFragment(Constant.TYPE_FINANCE)
            R.id.nav_zhihu -> replaceFragment(Constant.TYPE_ZHIHU)
            R.id.nav_user_add -> replaceFragment(Constant.TYPE_USERADD)
            R.id.nav_color_chooser -> createColorChooserDialog()
            R.id.nav_about -> AboutActivity.start(this)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    @Suppress("deprecation")
    private fun refreshUI() {
        val rootViewBackground = TypedValue()
        val textColorPrimary = TypedValue()
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true)
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
        nav_view.setBackgroundResource(rootViewBackground.resourceId)
        nav_view.itemTextColor = resources.getColorStateList(textColorPrimary.resourceId)
        nav_view.itemIconTintList = resources.getColorStateList(textColorPrimary.resourceId)
    }

    private fun replaceFragment(type: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragmentById = fragmentManager.findFragmentByTag(type.toString())
        if (fragmentById == null) {
            val fragment: Fragment = when {
                type != Constant.TYPE_USERADD -> ZhuanlanView.newInstance(type)
                else -> UserAddView()
            }
            fragmentTransaction.add(R.id.content_main, fragment, type.toString())
        } else {
            fragmentManager.fragments.forEach { fragment -> fragmentTransaction.hide(fragment) }
            fragmentTransaction.show(fragmentById)
        }
        fragmentTransaction.commit()
    }

    private fun createColorChooserDialog() {
        ColorChooserDialog.Builder(this, R.string.md_color_chooser_title)
                .doneButton(R.string.md_done)
                .cancelButton(R.string.md_cancel)
                .backButton(R.string.md_back)
                .allowUserColorInput(true)
                .allowUserColorInputAlpha(false)
                .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                .customButton(R.string.md_custom)
                .show()
    }

    override fun onColorSelection(dialog: ColorChooserDialog, @ColorInt selectedColor: Int) {
        if (supportActionBar != null)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(selectedColor))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = CircleView.shiftColorDown(selectedColor)
            window.navigationBarColor = selectedColor
        }
        if (!dialog.isAccentMode) {
            mSettingHelper.color = selectedColor
        }
    }

    override fun onColorChooserDismissed(dialog: ColorChooserDialog) {
        setUpSwitch()
    }

    private fun showAnimation() {
        val decorView = window.decorView
        val cacheBitmap = getCacheBitmapFromView(decorView)
        if (decorView is ViewGroup && cacheBitmap != null) {
            val view = View(this)
            view.background = BitmapDrawable(resources, cacheBitmap)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            decorView.addView(view, layoutParams)
            val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            objectAnimator.duration = 300
            objectAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    decorView.removeView(view)
                }
            })
            objectAnimator.start()
        }
    }

    private fun getCacheBitmapFromView(view: View): Bitmap? {
        val drawingCacheEnable = true
        view.isDrawingCacheEnabled = drawingCacheEnable
        view.buildDrawingCache(drawingCacheEnable)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache)
            view.isDrawingCacheEnabled = false
        } else {
            bitmap = null
        }
        return bitmap
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
