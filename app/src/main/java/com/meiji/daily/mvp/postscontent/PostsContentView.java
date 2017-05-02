package com.meiji.daily.mvp.postscontent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.meiji.daily.BaseActivity;
import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.utils.Api;

import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_SLUG;
import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_TITLE;
import static com.meiji.daily.bean.PostsListBean.POSTSLISTBEAN_TITLEIMAGE;

/**
 * Created by Meiji on 2016/11/22.
 */

public class PostsContentView extends BaseActivity implements IPostsContent.View {

    private WebView webView;
    private MaterialDialog dialog;

    private String titleImage;
    private String title;
    private int slug;
    private IPostsContent.Presenter presenter;

    public static void launch(String titleImage, String title, int slug) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, PostsContentView.class)
                .putExtra(POSTSLISTBEAN_TITLEIMAGE, titleImage)
                .putExtra(POSTSLISTBEAN_TITLE, title)
                .putExtra(POSTSLISTBEAN_SLUG, slug)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscontent);
        presenter = new PostsContentPresenter(this);
        initData();
        initView();
        initWebClient();
    }

    @Override
    public void onSetWebView(String url) {
        dialog.dismiss();
        webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
    }

    @Override
    public void onFail() {
        dialog.dismiss();
        Snackbar.make(webView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebClient() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(false);
        // 不调用第三方浏览器即可进行页面反应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        titleImage = intent.getStringExtra(POSTSLISTBEAN_TITLEIMAGE);
        title = intent.getStringExtra(POSTSLISTBEAN_TITLE);
        slug = intent.getIntExtra(POSTSLISTBEAN_SLUG, 0);
        presenter.doRequestData(slug);
    }

    private void initView() {
        ImageView iv_header = (ImageView) findViewById(R.id.iv_titleimage);
        Toolbar toolbar_title = (Toolbar) findViewById(R.id.toolbar_title);
        webView = (WebView) findViewById(R.id.webview_content);
        FloatingActionButton fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);

        setSupportActionBar(toolbar_title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain");
                String shareText = title + " " + Api.POST_URL + slug;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
            }
        });

        toolbar_layout.setTitle(title);
        toolbar_layout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbar_layout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        dialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.md_loading)
                .cancelable(true)
                .build();
        dialog.show();

        if (TextUtils.isEmpty(titleImage)) {
            iv_header.setImageResource(R.drawable.error_image);
            iv_header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            Glide.with(this).load(titleImage).centerCrop().into(iv_header);
        }
    }
}
