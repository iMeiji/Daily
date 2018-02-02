package com.meiji.daily.module.postslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.meiji.daily.App
import com.meiji.daily.R
import com.meiji.daily.bean.FooterBean
import com.meiji.daily.bean.PostsListBean
import com.meiji.daily.binder.FooterViewBinder
import com.meiji.daily.binder.PostsListViewBinder
import com.meiji.daily.module.base.BaseFragment
import com.meiji.daily.util.DiffCallback
import com.meiji.daily.util.OnLoadMoreListener
import com.meiji.daily.util.SettingHelper
import kotlinx.android.synthetic.main.activity_postslist.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Meiji on 2017/12/5.
 */

class PostsListView : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @field:[Inject Named("title")]
    lateinit var mTitle: String
    @Inject
    lateinit var mModel: PostsListViewModel
    @Inject
    lateinit var mSettingHelper: SettingHelper

    private var mRecyclerView: RecyclerView? = null
    private val mOldItems = Items()
    private var mAdapter: MultiTypeAdapter? = null
    private var mCanloadmore: Boolean = false

    override fun initInject() {
        DaggerPostsListComponent.builder()
                .appComponent(App.sAppComponent)
                .postsListModule(PostsListModule(this))
                .build().inject(this)
    }

    override fun attachLayoutId() = R.layout.activity_postslist


    override fun initViews(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_title)
        mRecyclerView = view.findViewById(R.id.recycler_view)
//        initToolBar(toolbar, true, mTitle!!)
        toolbar.setOnClickListener { mRecyclerView!!.smoothScrollToPosition(0) }
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeColors(mSettingHelper.color)
        refresh_layout.setOnRefreshListener(this)

        mAdapter = MultiTypeAdapter()
        mAdapter!!.register(PostsListBean::class.java, PostsListViewBinder())
        mAdapter!!.register(FooterBean::class.java, FooterViewBinder())
        mAdapter!!.items = mOldItems
        mRecyclerView!!.adapter = mAdapter
    }

    override fun subscribeUI() {
        mModel.listLiveData.observe(this, Observer<List<PostsListBean>> { list ->
            if (null != list && list.size > 0) {
                onSetAdapter(list)
            } else {
                onShowNetError()
            }
        })
        mModel.isLoading.observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                onShowLoading()
            } else {
                onHideLoading()
            }
        })
        mModel.isEnd.observe(this, Observer<Boolean> {
            Snackbar.make(refresh_layout, R.string.no_more, Snackbar.LENGTH_SHORT).show()
            if (mOldItems.size > 0) {
                mOldItems.removeAt(mOldItems.size - 1)
                mAdapter!!.notifyDataSetChanged()
            }
        })
    }

    fun onSetAdapter(list: List<PostsListBean>?) {
        val newItems = Items(list!!)
        newItems.add(FooterBean())

        DiffCallback.create(mOldItems, newItems, mAdapter!!)
        mOldItems.clear()
        mOldItems.addAll(newItems)

        mCanloadmore = true

        mRecyclerView!!.addOnScrollListener(object : OnLoadMoreListener() {
            override fun onLoadMore() {
                if (mCanloadmore) {
                    mCanloadmore = false
                    mModel.loadMore()
                }
            }
        })
    }

    override fun onRefresh() {
        mModel.doRefresh()
    }

    fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }

    fun onShowNetError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        const val ARGUMENT_SLUG = "ARGUMENT_SLUG"
        const val ARGUMENT_NAME = "ARGUMENT_NAME"
        const val ARGUMENT_POSTSCOUNT = "ARGUMENT_POSTSCOUNT"
        const val TAG = "PostsListView"

        fun newInstance(slug: String, title: String, postsCount: Int): PostsListView {
            val args = Bundle()
            args.putString(ARGUMENT_SLUG, slug)
            args.putString(ARGUMENT_NAME, title)
            args.putInt(ARGUMENT_POSTSCOUNT, postsCount)
            val fragment = PostsListView()
            fragment.arguments = args
            return fragment
        }
    }
}