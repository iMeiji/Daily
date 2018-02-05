package com.meiji.daily.module.useradd

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_useradd.*
import kotlinx.android.synthetic.main.fragment_useradd.view.*
import kotlinx.android.synthetic.main.item_zhuanlan.view.*
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

    private lateinit var mDialog: MaterialDialog

    private var mList: MutableList<ZhuanlanBean>? = null
    private var mAdapter: MultiTypeAdapter? = null
    private var mIsdelete: Boolean = false

    override fun initInject() {
        DaggerUserAddComponent.builder()
                .appComponent(App.sAppComponent)
                .userAddModule(UserAddModule(this))
                .build().inject(this)
    }

    override fun attachLayoutId() = R.layout.fragment_useradd

    override fun subscribeUI() {
        mModel.mList.observe(this, Observer { list ->
            if (null != list && list.size > 0) {
                onSetAdapter(list)
            }
        })
        mModel.isLoading.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                onShowLoading()
            } else {
                onHideLoading()
            }
        })
        mModel.isRefreshUI.observe(this, Observer { refreshUI() })
        mModel.isAddResult.observe(this, Observer { aBoolean ->
            if (aBoolean!!) {
                onAddSuccess()
            } else {
                onAddFail()
            }
        })
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        fab_add.backgroundTintList = ColorStateList.valueOf(mSettingHelper.color)
        refresh_layout.setColorSchemeColors(mSettingHelper.color)
    }

    override fun onStop() {
        super.onStop()
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun initViews(view: View) {
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.layoutManager = LinearLayoutManager(activity)

        // 设置下拉刷新的按钮的颜色
        view.refresh_layout.setColorSchemeColors(mSettingHelper.color)
        view.refresh_layout.setOnRefreshListener(this)

        view.fab_add.backgroundTintList = ColorStateList.valueOf(mSettingHelper.color)
        view.fab_add.setOnClickListener(this)

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
                        Snackbar.make(recycler_view, getString(R.string.deleted) + name, Snackbar.LENGTH_LONG)
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
        helper.attachToRecyclerView(recycler_view)
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
        ll_root.setBackgroundResource(rootViewBackground.resourceId)
        tv_description.setTextColor(resources.getColor(textColorPrimary.resourceId))

        val childCount = recycler_view.childCount
        for (i in 0 until childCount) {
            val cardView = recycler_view.getChildAt(i).cardview
            cardView.setBackgroundResource(itemViewBackground.resourceId)

            cardView.tv_name.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_followersCount.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_postsCount.setTextColor(resources.getColor(textColorPrimary.resourceId))
            cardView.tv_intro.setTextColor(resources.getColor(textColorPrimary.resourceId))
        }

        RecyclerViewUtil.invalidateCacheItem(recycler_view)
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
        mDialog.setActionButton(DialogAction.NEGATIVE, R.string.md_cancel)
        mDialog.setActionButton(DialogAction.POSITIVE, R.string.md_ok)
        mDialog.setActionButton(DialogAction.NEUTRAL, R.string.md_zhuanlan_add_help)

        mDialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener { mDialog.dismiss() }

        mDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener {
            // 校验填写 id 是否正确
            onCheckInputId()
            mDialog.dismiss()
        }

        mDialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener {
            // 什么是专栏 id
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.add_zhuanlan_id_help_url))))
            mDialog.dismiss()
        }

        mDialog.show()
    }

    fun onCheckInputId() {
        val input = mDialog.inputEditText?.text.toString()
        if (!TextUtils.isEmpty(input)) {
            mModel.addItem(input.trim().toLowerCase())
        }
    }

    fun onSetAdapter(list: MutableList<ZhuanlanBean>?) {
        if (mAdapter == null) {
            mAdapter = MultiTypeAdapter()
            mAdapter?.register(ZhuanlanBean::class.java, ZhuanlanViewBinder())
            recycler_view.adapter = mAdapter
        }
        mList = list
        mAdapter!!.items = mList!!
        mAdapter!!.notifyDataSetChanged()

        if (mList!!.size == 0) {
            tv_description.visibility = View.VISIBLE
        } else {
            tv_description.visibility = View.GONE
        }
    }

    override fun onRefresh() {
        mModel.handleData()
    }

    fun onAddSuccess() {
        Snackbar.make(recycler_view, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show()
        tv_description.visibility = View.GONE
    }

    fun onShowLoading() {
        refresh_layout.isRefreshing = true
    }

    fun onHideLoading() {
        refresh_layout.isRefreshing = false
    }

    fun onAddFail() {
        Snackbar.make(recycler_view, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show()
        refresh_layout.isEnabled = true
    }
}
