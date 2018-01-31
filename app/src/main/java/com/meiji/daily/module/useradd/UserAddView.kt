package com.meiji.daily.module.useradd

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
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
 * Created by Meiji on 2017/12/4.
 */

class UserAddView : BaseFragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var mModel: UserAddViewModel
    @Inject
    lateinit var mSettingHelper: SettingHelper
    @Inject
    lateinit var mSharedPreferences: SharedPreferences

    private var mTvDesc: TextView? = null
    private var mRecyclerView: RecyclerView? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var mFab: FloatingActionButton? = null
    private var mDialog: MaterialDialog? = null
    private var mRoot: LinearLayout? = null

    private var mList: MutableList<ZhuanlanBean>? = null
    private var mAdapter: MultiTypeAdapter? = null
    private var mIsdelete: Boolean = false

    override fun initInject() {
        DaggerUserAddComponent.builder()
                .appComponent(App.sAppComponent)
                .userAddModule(UserAddModule(this))
                .build().inject(this)
    }

    override fun attachLayoutId(): Int {
        return R.layout.fragment_useradd
    }

    override fun subscribeUI() {
        mModel.mList!!.observe(this, Observer { list ->
            if (null != list && list.size > 0) {
                onSetAdapter(list)
            }
        })
        mModel.isLoading!!.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                onShowLoading()
            } else {
                onHideLoading()
            }
        })
        mModel.isRefreshUI!!.observe(this, Observer { refreshUI() })
        mModel.isAddResult!!.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                onAddSuccess()
            } else {
                onAddFail()
            }
        })
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "color") {
            mFab!!.backgroundTintList = ColorStateList.valueOf(mSettingHelper.color)
        }
    }

    override fun onStop() {
        super.onStop()
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun initViews(view: View) {
        mTvDesc = view.findViewById(R.id.tv_description)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mFab = view.findViewById(R.id.fab_add)
        mRefreshLayout = view.findViewById(R.id.refresh_layout)
        mRoot = view.findViewById(R.id.root)

        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

        // 设置下拉刷新的按钮的颜色
        mRefreshLayout!!.setColorSchemeColors(mSettingHelper.color)
        mRefreshLayout!!.setOnRefreshListener(this)

        mFab!!.backgroundTintList = ColorStateList.valueOf(mSettingHelper.color)
        mFab!!.setOnClickListener(this)

        val helper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        val bean = mList!![position]
                        val name = mList!![position].name
                        mList!!.remove(bean)
                        mAdapter!!.notifyDataSetChanged()
                        mIsdelete = true
                        Snackbar.make(mRecyclerView!!, getString(R.string.deleted) + name, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo)) {
                                    mIsdelete = false
                                    mModel.handleData()
                                }
                                .show()
                        Handler().postDelayed({
                            if (mIsdelete) {
                                mModel.deleteItem(bean)
                            }
                        }, 1500)
                    }
                })
        helper.attachToRecyclerView(mRecyclerView)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.fab_add) {
            createDialog()
        }
    }

    private fun refreshUI() {
        val theme = activity!!.theme
        val resources = resources
        val rootViewBackground = TypedValue()
        val itemViewBackground = TypedValue()
        val textColorPrimary = TypedValue()
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true)
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true)
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true)
        mRoot!!.setBackgroundResource(rootViewBackground.resourceId)
        mTvDesc!!.setTextColor(resources.getColor(textColorPrimary.resourceId))

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

    private fun createDialog() {
        mDialog = MaterialDialog.Builder(activity!!)
                .title(R.string.md_zhuanlan_add_title)
                .content(R.string.md_zhuanlan_add_content)
                .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                .inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .input("", "") { dialog, input -> }
                .build()

        // 设置3个按键
        mDialog!!.setActionButton(DialogAction.NEGATIVE, R.string.md_cancel)
        mDialog!!.setActionButton(DialogAction.POSITIVE, R.string.md_ok)
        mDialog!!.setActionButton(DialogAction.NEUTRAL, R.string.md_zhuanlan_add_help)

        mDialog!!.getActionButton(DialogAction.NEGATIVE).setOnClickListener { mDialog!!.dismiss() }

        mDialog!!.getActionButton(DialogAction.POSITIVE).setOnClickListener {
            // 校验填写 id 是否正确
            onCheckInputId()
            mDialog!!.dismiss()
        }

        mDialog!!.getActionButton(DialogAction.NEUTRAL).setOnClickListener {
            // 什么是专栏 id
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.add_zhuanlan_id_help_url))))
            mDialog!!.dismiss()
        }

        mDialog!!.show()
    }

    fun onCheckInputId() {
        val input = mDialog!!.inputEditText!!.text.toString()
        if (!TextUtils.isEmpty(input)) {
            mModel.addItem(input.trim { it <= ' ' }.toLowerCase())
        }
    }

    fun onSetAdapter(list: MutableList<ZhuanlanBean>?) {
        if (mAdapter == null) {
            mAdapter = MultiTypeAdapter()
            mAdapter!!.register(ZhuanlanBean::class.java, ZhuanlanViewBinder())
            mRecyclerView!!.adapter = mAdapter
        }
        mList = list
        mAdapter!!.items = mList!!
        mAdapter!!.notifyDataSetChanged()

        if (mList!!.size == 0) {
            mTvDesc!!.visibility = View.VISIBLE
        } else {
            mTvDesc!!.visibility = View.GONE
        }
    }

    override fun onRefresh() {
        mModel.handleData()
    }

    fun onAddSuccess() {
        Snackbar.make(mRecyclerView!!, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show()
        mTvDesc!!.visibility = View.GONE
    }

    fun onShowLoading() {
        mRefreshLayout!!.isRefreshing = true
    }

    fun onHideLoading() {
        mRefreshLayout!!.isRefreshing = false
    }

    fun onAddFail() {
        Snackbar.make(mRecyclerView!!, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show()
        mRefreshLayout!!.isEnabled = true
    }
}
