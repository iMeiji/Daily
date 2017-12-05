package com.meiji.daily.mvp.postslist;

import android.content.Intent;

import com.meiji.daily.Constant;
import com.meiji.daily.InitApp;
import com.meiji.daily.R;
import com.meiji.daily.mvp.base.BaseNewActivity;

/**
 * Created by Meiji on 2017/12/5.
 */

public class PostsListActivity extends BaseNewActivity {

    public static void launch(String slug, String title, int postsCount) {
        InitApp.AppContext.startActivity(new Intent(InitApp.AppContext, PostsListActivity.class)
                .putExtra(Constant.ZHUANLANBEAN_SLUG, slug)
                .putExtra(Constant.ZHUANLANBEAN_NAME, title)
                .putExtra(Constant.ZHUANLANBEAN_POSTSCOUNT, postsCount)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
        String slug = intent.getStringExtra(Constant.ZHUANLANBEAN_SLUG);
        int postCount = intent.getIntExtra(Constant.ZHUANLANBEAN_POSTSCOUNT, 0);
        String title = intent.getStringExtra(Constant.ZHUANLANBEAN_NAME);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PostsListNewView.newInstance(slug, title, postCount))
                .commit();
    }
}
