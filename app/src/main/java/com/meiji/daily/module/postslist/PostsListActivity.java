package com.meiji.daily.module.postslist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.meiji.daily.R;
import com.meiji.daily.module.base.BaseActivity;

/**
 * Created by Meiji on 2017/12/5.
 */

public class PostsListActivity extends BaseActivity {

    private static final String EXTRA_SLUG = "EXTRA_SLUG";
    private static final String EXTRA_NAME = "EXTRA_NAME";
    private static final String EXTRA_POSTSCOUNT = "EXTRA_POSTSCOUNT";

    public static void start(@NonNull Context context, @NonNull String slug, @NonNull String title, int postsCount) {
        Intent starter = new Intent(context, PostsListActivity.class)
                .putExtra(EXTRA_SLUG, slug)
                .putExtra(EXTRA_NAME, title)
                .putExtra(EXTRA_POSTSCOUNT, postsCount);
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
        String slug = intent.getStringExtra(EXTRA_SLUG);
        String title = intent.getStringExtra(EXTRA_NAME);
        int postCount = intent.getIntExtra(EXTRA_POSTSCOUNT, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PostsListView.newInstance(slug, title, postCount))
                .commit();
    }
}
