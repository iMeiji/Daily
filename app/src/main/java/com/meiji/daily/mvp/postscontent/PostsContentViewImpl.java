package com.meiji.daily.mvp.postscontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.meiji.daily.R;
import com.meiji.daily.mvp.postscontent.presenter.IPostsContentPresenter;
import com.meiji.daily.mvp.postscontent.presenter.PostsContentPresenterImpl;
import com.meiji.daily.mvp.postscontent.view.IPostsContentView;
import com.meiji.daily.utils.Api;

import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_SLUG;
import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_TITLE;
import static com.meiji.daily.mvp.postslist.model.PostsListBean.POSTSLISTBEAN_TITLEIMAGE;

/**
 * Created by Meiji on 2016/11/22.
 */

public class PostsContentViewImpl extends AppCompatActivity implements View.OnClickListener, IPostsContentView {

    private ImageView iv_header;
    private Toolbar toolbar;
    private AppBarLayout app_bar;
    private WebView webView;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout toolbarLayout;
    private String titleImage;
    private String title;
    private int slug;
    private MaterialDialog progressDialog;
    private IPostsContentPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscontent);
        presenter = new PostsContentPresenterImpl(this);
        initData();
        initView();
        initWebClient();
    }

    @Override
    public void onSetWebView(String url) {
        webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        progressDialog.dismiss();
    }

    @Override
    public void onFail() {
        Snackbar.make(getCurrentFocus(), "加载错误", Snackbar.LENGTH_SHORT);
    }

    private void initWebClient() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        //缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        //开启application Cache功能
        settings.setAppCacheEnabled(false);
        //不调用第三方浏览器即可进行页面反应
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
        iv_header = (ImageView) findViewById(R.id.iv_header);
        toolbar = (Toolbar) findViewById(R.id.toolbar_postscontent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        webView = (WebView) findViewById(R.id.wb_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbarlayout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        progressDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.loading)
                .cancelable(true)
                .build();
        progressDialog.show();

        if (TextUtils.isEmpty(titleImage)) {
            iv_header.setImageResource(R.drawable.error_image);
            iv_header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            Glide.with(this).load(titleImage).centerCrop().into(iv_header);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain");
                String shareText = title + " " + Api.POST_URL + slug;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
