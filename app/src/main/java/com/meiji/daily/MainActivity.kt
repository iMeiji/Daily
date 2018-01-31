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
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
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
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {
    @Inject
    lateinit var mRxBusHelper: RxBusHelper
    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mSwitchCompat: SwitchCompat? = null
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

    override fun attachLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        replaceFragment(Constant.TYPE_PRODUCT)
        mNavigationView!!.setCheckedItem(R.id.nav_product)
    }

    override fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolBar(toolbar, false, null)

        mDrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        mNavigationView = findViewById(R.id.nav_view)
        mNavigationView!!.setNavigationItemSelectedListener(this)

        mSwitchCompat = mNavigationView!!.menu.findItem(R.id.app_bar_switch).actionView.findViewById(R.id.switch_input)
        mSwitchCompat!!.isChecked = mSettingHelper.isNightMode

        setUpSwitch()

        mSwitchCompat!!.setOnCheckedChangeListener { compoundButton, isNightMode ->
            mSettingHelper.isNightMode = isNightMode
            setUpSwitch()
            if (isNightMode) {
                setTheme(R.style.DarkTheme)
            } else {
                setTheme(R.style.LightTheme)
            }
            mRxBusHelper.post(Constant.RxBusEvent.REFRESHUI, isNightMode)
        }
    }

    private fun setUpSwitch() {
        val isNightMode = mSettingHelper.isNightMode
        if (isNightMode) {
            mSwitchCompat!!.thumbTintList = ColorStateList.valueOf(mSettingHelper.color)
        } else {
            val theme = theme
            val resources = resources
            val textColorPrimary = TypedValue()
            theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
            mSwitchCompat!!.thumbTintList = resources.getColorStateList(textColorPrimary.resourceId)
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        } else if (currentTime - mExitTime < 2000) {
            super.onBackPressed()
        } else {
            Snackbar.make(mDrawerLayout!!, getString(R.string.double_click_exit), Snackbar.LENGTH_SHORT).show()
            mExitTime = currentTime
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_product) {
            replaceFragment(Constant.TYPE_PRODUCT)

        } else if (id == R.id.nav_life) {
            replaceFragment(Constant.TYPE_LIFE)

        } else if (id == R.id.nav_music) {
            replaceFragment(Constant.TYPE_MUSIC)

        } else if (id == R.id.nav_emotion) {
            replaceFragment(Constant.TYPE_EMOTION)

        } else if (id == R.id.nav_profession) {
            replaceFragment(Constant.TYPE_FINANCE)

        } else if (id == R.id.nav_zhihu) {
            replaceFragment(Constant.TYPE_ZHIHU)

        } else if (id == R.id.nav_user_add) {
            replaceFragment(Constant.TYPE_USERADD)

        } else if (id == R.id.nav_color_chooser) {
            createColorChooserDialog()

        } else if (id == R.id.nav_about) {
            AboutActivity.start(this)

        }

        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    protected fun refreshUI() {
        val theme = theme
        val rootViewBackground = TypedValue()
        val textColorPrimary = TypedValue()
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true)
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
        val resources = resources
        mNavigationView!!.setBackgroundResource(rootViewBackground.resourceId)
        mNavigationView!!.setItemBackgroundResource(rootViewBackground.resourceId)
        mNavigationView!!.itemTextColor = resources.getColorStateList(textColorPrimary.resourceId)
        mNavigationView!!.itemIconTintList = resources.getColorStateList(textColorPrimary.resourceId)
    }

    private fun replaceFragment(type: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragmentById = fragmentManager.findFragmentByTag(type.toString())
        if (fragmentById == null) {
            val fragment: Fragment
            if (type != Constant.TYPE_USERADD) {
                fragment = ZhuanlanView.newInstance(type)
            } else {
                fragment = UserAddView()
            }
            fragmentTransaction
                    .add(R.id.content_main, fragment, type.toString())
        } else {
            for (fragment in fragmentManager.fragments) {
                fragmentTransaction.hide(fragment)
            }
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
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(selectedColor))
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
        val decorview = window.decorView
        val cacheBitmap = getCacheBitmapFromView(decorview)
        if (decorview is ViewGroup && cacheBitmap != null) {
            val view = View(this)
            view.background = BitmapDrawable(resources, cacheBitmap)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            decorview.addView(view, layoutParams)
            val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            objectAnimator.duration = 300
            objectAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    decorview.removeView(view)
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

        internal val TAG = "MainActivity"
    }
}
