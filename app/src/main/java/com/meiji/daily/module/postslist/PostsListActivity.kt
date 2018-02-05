package com.meiji.daily.module.postslist

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.meiji.daily.R
import com.meiji.daily.module.base.BaseActivity

/**
 * Created by Meiji on 2017/12/5.
 */

class PostsListActivity : BaseActivity() {

    override fun attachLayoutId() = R.layout.container

    override fun initViews() {}

    override fun initData(savedInstanceState: Bundle?) {
        if (intent == null) {
            finish()
            return
        }
        val slug = intent.getStringExtra(EXTRA_SLUG)
        val title = intent.getStringExtra(EXTRA_NAME)
        val postCount = intent.getIntExtra(EXTRA_POSTSCOUNT, 0)
//        supportActionBar?.title = title
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PostsListView.newInstance(slug, title, postCount))
                    .commit()
        }
    }

    companion object {

        private val EXTRA_SLUG = "EXTRA_SLUG"
        private val EXTRA_NAME = "EXTRA_NAME"
        private val EXTRA_POSTSCOUNT = "EXTRA_POSTSCOUNT"

        fun start(context: Context, slug: String, title: String, postsCount: Int) {
            val starter = Intent(context, PostsListActivity::class.java)
                    .putExtra(EXTRA_SLUG, slug)
                    .putExtra(EXTRA_NAME, title)
                    .putExtra(EXTRA_POSTSCOUNT, postsCount)
            context.startActivity(starter)
        }
    }
}
