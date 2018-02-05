package com.meiji.daily.module.postscontent

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.meiji.daily.R
import com.meiji.daily.module.base.BaseActivity

/**
 * Created by Meiji on 2017/12/6.
 */

class PostsContentActivity : BaseActivity() {

    override fun attachLayoutId() = R.layout.container

    override fun initViews() {

    }

    override fun initData(savedInstanceState: Bundle?) {
        if (intent == null) {
            finish()
            return
        }
        val titleImage = intent.getStringExtra(EXTRA_TITLEIMAGE)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val slug = intent.getStringExtra(EXTRA_SLUG)
//        if (supportActionBar != null) {
//            supportActionBar!!.title = title
//        }
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PostsContentView.newInstance(titleImage, title, slug))
                    .commit()
        }
    }

    companion object {

        internal val EXTRA_TITLEIMAGE = "EXTRA_TITLEIMAGE"
        internal val EXTRA_TITLE = "EXTRA_TITLE"
        internal val EXTRA_SLUG = "EXTRA_SLUG"

        fun start(context: Context, titleImage: String, title: String, slug: String) {
            val starter = Intent(context, PostsContentActivity::class.java)
            starter.putExtra(EXTRA_TITLEIMAGE, titleImage)
            starter.putExtra(EXTRA_TITLE, title)
            starter.putExtra(EXTRA_SLUG, slug)
            context.startActivity(starter)
        }
    }
}
