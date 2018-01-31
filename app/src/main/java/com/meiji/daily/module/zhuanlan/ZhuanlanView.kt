package com.meiji.daily.module.zhuanlan

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.View
import com.meiji.daily.App
import com.meiji.daily.R
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.binder.ZhuanlanViewBinder
import com.meiji.daily.module.base.BaseFragment
import com.meiji.daily.util.RecyclerViewUtil
import com.meiji.daily.util.SettingHelper
import kotlinx.android.synthetic.main.fragment_zhuanlan.*
import kotlinx.android.synthetic.main.item_zhuanlan.view.*
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

    private var mAdapter: MultiTypeAdapter? = null

    private fun refreshUI() {
        val theme = activity!!.theme
        val rootViewBackground = TypedValue()
        val itemViewBackground = TypedValue()
        val textColorPrimary = TypedValue()
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true)
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true)
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
        ll_root!!.setBackgroundResource(rootViewBackground.resourceId)

        val resources = resources
        val childCount = recycler_view!!.childCount
        for (i in 0 until childCount) {
            val cardView = recycler_view!!.getChildAt(i).cardview
            cardView.setBackgroundResource(itemViewBackground.resourceId)

            cardView.tv_name.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_followersCount.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_postsCount.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_intro.setTextColor(resources.getColor(textColorPrimary.resourceId))
        }
        RecyclerViewUtil.invalidateCacheItem(recycler_view)
    }

    override fun initInject() {
        DaggerZhuanlanComponent.builder()
                .appComponent(App.sAppComponent)
                .zhuanlanModule(ZhuanlanModule(this))
                .build().inject(this)
    }

    override fun attachLayoutId() = R.layout.fragment_zhuanlan

    override fun initViews(view: View) {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        // 设置下拉刷新的按钮的颜色
        refresh_layout.setColorSchemeColors(mSettingHelper.color)
        refresh_layout.setOnRefreshListener(this)
    }

    override fun subscribeUI() {
        mModel.mList.observe(this, Observer<List<ZhuanlanBean>> { list ->
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
        mModel.isRefreshUI.observe(this, Observer<Boolean> { refreshUI() })
    }

    private fun onSetAdapter(list: List<ZhuanlanBean>) {
        if (mAdapter == null) {
            mAdapter = MultiTypeAdapter(list)
            mAdapter?.register(ZhuanlanBean::class.java, ZhuanlanViewBinder())
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        mModel.handleData()
    }

    private fun onShowLoading() {
        refresh_layout.isRefreshing = true
        recycler_view.visibility = View.GONE
    }

    private fun onHideLoading() {
        refresh_layout.isRefreshing = false
        recycler_view.visibility = View.VISIBLE
    }

    private fun onShowNetError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
        refresh_layout.isEnabled = true
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
