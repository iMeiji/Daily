package com.meiji.daily.module.zhuanlan

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.meiji.daily.App
import com.meiji.daily.R
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.binder.ZhuanlanViewBinder
import com.meiji.daily.module.base.BaseFragment
import com.meiji.daily.util.RecyclerViewUtil
import com.meiji.daily.util.SettingHelper
import me.drakeet.multitype.MultiTypeAdapter
import javax.inject.Inject


/**
 * Created by Meiji on 2017/11/29.
 */

class ZhuanlanView : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var mModel: ZhuanlanViewModel
    @Inject
    lateinit var mSettingHelper: SettingHelper

    private var mRecyclerView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mRoot: LinearLayout? = null
    private var mAdapter: MultiTypeAdapter? = null

    private fun refreshUI() {
        val theme = activity!!.theme
        val rootViewBackground = TypedValue()
        val itemViewBackground = TypedValue()
        val textColorPrimary = TypedValue()
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true)
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true)
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
        mRoot!!.setBackgroundResource(rootViewBackground.resourceId)

        val resources = resources
        val childCount = mRecyclerView!!.childCount
        for (i in 0 until childCount) {
            val cardView = mRecyclerView!!.getChildAt(i).findViewById<CardView>(R.id.cardview)
            cardView.setBackgroundResource(itemViewBackground.resourceId)

            val tv_name = cardView.findViewById<TextView>(R.id.tv_name)
            tv_name.setTextColor(resources.getColor(textColorPrimary.resourceId))

            val tv_followersCount = cardView.findViewById<TextView>(R.id.tv_followersCount)
            tv_followersCount.setTextColor(resources.getColor(textColorPrimary.resourceId))

            val tv_postsCount = cardView.findViewById<TextView>(R.id.tv_postsCount)
            tv_postsCount.setTextColor(resources.getColor(textColorPrimary.resourceId))

            val tv_intro = cardView.findViewById<TextView>(R.id.tv_intro)
            tv_intro.setTextColor(resources.getColor(textColorPrimary.resourceId))
        }

        RecyclerViewUtil.invalidateCacheItem(mRecyclerView)
    }

    override fun initInject() {
        DaggerZhuanlanComponent.builder()
                .appComponent(App.sAppComponent)
                .zhuanlanModule(ZhuanlanModule(this))
                .build().inject(this)
    }

    override fun attachLayoutId(): Int {
        return R.layout.fragment_zhuanlan
    }

    override fun initViews(view: View) {
        mRoot = view.findViewById(R.id.root)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_layout)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        // 设置下拉刷新的按钮的颜色
        mSwipeRefreshLayout!!.setColorSchemeColors(mSettingHelper.color)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
    }

    override fun subscribeUI() {
        mModel.mList!!.observe(this, Observer<List<ZhuanlanBean>> { list ->
            if (null != list && list.size > 0) {
                onSetAdapter(list)
            } else {
                onShowNetError()
            }
        })
        mModel.isLoading!!.observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                onShowLoading()
            } else {
                onHideLoading()
            }
        })
        mModel.isRefreshUI!!.observe(this, Observer<Boolean> { refreshUI() })
    }

    private fun onSetAdapter(list: List<ZhuanlanBean>?) {
        if (mAdapter == null) {
            mAdapter = MultiTypeAdapter(list!!)
            mAdapter!!.register(ZhuanlanBean::class.java, ZhuanlanViewBinder())
            mRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        mModel.handleData()
    }

    private fun onShowLoading() {
        mSwipeRefreshLayout!!.isRefreshing = true
        mRecyclerView!!.visibility = View.GONE
    }

    private fun onHideLoading() {
        mSwipeRefreshLayout!!.isRefreshing = false
        mRecyclerView!!.visibility = View.VISIBLE
    }

    private fun onShowNetError() {
        Snackbar.make(mSwipeRefreshLayout!!, R.string.network_error, Snackbar.LENGTH_SHORT).show()
        mSwipeRefreshLayout!!.isEnabled = true
    }

    companion object {

        internal val ARGUMENT_TYPE = "ARGUMENT_TYPE"
        internal val TAG = "ZhuanlanView"

        fun newInstance(type: Int): ZhuanlanView {
            val args = Bundle()
            args.putInt(ARGUMENT_TYPE, type)
            val fragment = ZhuanlanView()
            fragment.arguments = args
            return fragment
        }
    }
}
