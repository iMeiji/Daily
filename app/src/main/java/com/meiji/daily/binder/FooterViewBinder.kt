package com.meiji.daily.binder

import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.meiji.daily.App
import com.meiji.daily.R
import com.meiji.daily.bean.FooterBean
import com.meiji.daily.util.SettingHelper
import dagger.Lazy
import kotlinx.android.synthetic.main.item_loading.view.*
import me.drakeet.multitype.ItemViewBinder
import javax.inject.Inject

/**
 * Created by Meiji on 2017/6/7.
 */

internal class FooterViewBinder : ItemViewBinder<FooterBean, FooterViewBinder.ViewHolder>() {

    @Inject
    internal lateinit var mSettingHelper: Lazy<SettingHelper>

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): FooterViewBinder.ViewHolder {
        DaggerItemViewComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this)
        val view = inflater.inflate(R.layout.item_loading, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: FooterBean) {
        val color = mSettingHelper.get().color
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val wrapDrawable = DrawableCompat.wrap(holder.mProgressBar.indeterminateDrawable)
            DrawableCompat.setTint(wrapDrawable, color)
            holder.mProgressBar.indeterminateDrawable = DrawableCompat.unwrap(wrapDrawable)
        } else {
            holder.mProgressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mProgressBar: ProgressBar = itemView.loading

    }
}
