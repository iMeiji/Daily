package com.meiji.daily.module.postscontent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.meiji.daily.R;
import com.meiji.daily.module.base.BaseNewActivity;

/**
 * Created by Meiji on 2017/12/6.
 */

public class PostsContentActivity extends BaseNewActivity {

    private static final String EXTRA_TITLEIMAGE = "EXTRA_TITLEIMAGE";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_SLUG = "EXTRA_SLUG";

    public static void start(@NonNull Context context, @NonNull String titleImage, @NonNull String title, int slug) {
        Intent starter = new Intent(context, PostsContentActivity.class);
        starter.putExtra(EXTRA_TITLEIMAGE, titleImage);
        starter.putExtra(EXTRA_TITLE, title);
        starter.putExtra(EXTRA_SLUG, slug);
        context.startActivity(starter);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.container;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        String titleImage = intent.getStringExtra(EXTRA_TITLEIMAGE);
        String title = intent.getStringExtra(EXTRA_TITLE);
        int slug = intent.getIntExtra(EXTRA_SLUG, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PostsContentNewView.newInstance(titleImage, title, slug))
                .commit();
    }
}
