package com.meiji.daily.module.postscontent

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.meiji.daily.App
import com.meiji.daily.GlideApp
import com.meiji.daily.R
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.module.base.BaseFragment
import com.meiji.daily.util.SettingHelper
import kotlinx.android.synthetic.main.activity_postscontent.view.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Meiji on 2017/12/6.
 */

class PostsContentView : BaseFragment() {

    @field:[Inject Named("image")]
    lateinit var mImage: String

    @field:[Inject Named("title")]
    lateinit var mTitle: String

    @field:[Inject Named("slug")]
    lateinit var mSlug: String

    @Inject
    lateinit var mModel: PostsContentViewModel
    @Inject
    lateinit var mSettingHelper: SettingHelper

    private lateinit var mWebView: WebView
    private var mDialog: MaterialDialog? = null

    override fun initInject() {
        DaggerPostsContentComponent.builder()
                .appComponent(App.sAppComponent)
                .postsContentModule(com.meiji.daily.module.postscontent.PostsContentModule(this))
                .build().inject(this)
    }

    fun onSetWebView(url: String?) {
        mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebClient() {
        val settings = mWebView.settings
        settings.javaScriptEnabled = true
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.builtInZoomControls = false
        // 缓存
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        // 开启DOM storage API功能
        settings.domStorageEnabled = true
        // 开启application Cache功能
        settings.setAppCacheEnabled(false)
        // 不调用第三方浏览器即可进行页面反应
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        mWebView.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun attachLayoutId() = R.layout.activity_postscontent

    override fun initViews(view: View) {
        mWebView = view.webview_content

        initToolBar(view.toolbar_title, true, mTitle)

        view.toolbar_title.setOnClickListener { view.scrollView.smoothScrollTo(0, 0) }

        view.fab_share.backgroundTintList = ColorStateList.valueOf(mSettingHelper.color)
        view.fab_share.setOnClickListener {
            val shareIntent = Intent().also { it.action = Intent.ACTION_SEND; it.type = "text/plain" }
            val shareText = mTitle + " " + IApi.POST_URL + mSlug
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)))
        }

        view.collapsing_layout.let {
            it.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
            it.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
            it.title = mTitle
        }

        mDialog = MaterialDialog.Builder(context!!)
                .progress(true, 0)
                .content(R.string.md_loading)
                .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                .cancelable(true)
                .build()

        if (TextUtils.isEmpty(mImage)) {
            view.iv_titleimage.setImageResource(R.drawable.error_image)
            view.iv_titleimage.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            GlideApp.with(this)
                    .load(mImage)
                    .centerCrop()
                    .error(R.color.viewBackground)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(view.iv_titleimage)
        }

        initWebClient()
    }

    override fun subscribeUI() {
        mModel.html.observe(this, Observer { s ->
            if (!TextUtils.isEmpty(s)) {
                onSetWebView(s)
            } else {
                onShowNetError()
            }
        })
        mModel.isLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                onShowLoading()
            } else {
                onHideLoading()
            }
        })
    }

    fun onShowLoading() {
        mDialog!!.show()
    }

    fun onHideLoading() {
        mDialog!!.dismiss()
    }

    fun onShowNetError() {
        mDialog!!.dismiss()
        Snackbar.make(mWebView, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        internal val ARGUMENT_TITLEIMAGE = "ARGUMENT_TITLEIMAGE"
        internal val ARGUMENT_TITLE = "ARGUMENT_TITLE"
        internal val ARGUMENT_SLUG = "ARGUMENT_SLUG"

        fun newInstance(titleImage: String, title: String, slug: String): PostsContentView {
            val args = Bundle()
            args.putString(ARGUMENT_TITLEIMAGE, titleImage)
            args.putString(ARGUMENT_TITLE, title)
            args.putString(ARGUMENT_SLUG, slug)
            val fragment = com.meiji.daily.module.postscontent.PostsContentView()
            fragment.arguments = args
            return fragment
        }
    }
}
