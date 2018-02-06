package com.meiji.daily.binder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.meiji.daily.GlideApp
import com.meiji.daily.R
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.module.postslist.PostsListActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_zhuanlan.view.*
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by Meiji on 2017/6/6.
 */

internal class ZhuanlanViewBinder : ItemViewBinder<ZhuanlanBean, ZhuanlanViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ZhuanlanViewBinder.ViewHolder {
        val view = inflater.inflate(R.layout.item_zhuanlan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZhuanlanViewBinder.ViewHolder, item: ZhuanlanBean) {
        val context = holder.itemView.context
        val followersCount = item.followersCount.toString() + "人关注TA"
        val postsCount = item.postsCount.toString() + "篇文章"
        var avatarUrl = item.avatar?.template
        if (!TextUtils.isEmpty(avatarUrl)) {
            // 拼凑avatar链接
            avatarUrl = avatarUrl
                    ?.replace("{id}", item.avatar?.id.toString())
                    ?.replace("{size}", "m")

            GlideApp.with(context)
                    .load(avatarUrl)
                    .centerCrop()
                    .error(R.color.viewBackground)
                    .into(holder.cv_avatar)
        }
        holder.tv_name.text = item.name
        holder.tv_followersCount.text = followersCount
        holder.tv_postsCount.text = postsCount
        holder.tv_intro.text = item.intro
        holder.cardView.setOnClickListener { PostsListActivity.start(context, item.slug, item.name!!, item.postsCount) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView = itemView.cardview
        val cv_avatar: CircleImageView = itemView.cv_avatar
        val tv_name: TextView = itemView.tv_name
        val tv_followersCount: TextView = itemView.tv_followersCount
        val tv_postsCount: TextView = itemView.tv_postsCount
        val tv_intro: TextView = itemView.tv_intro

    }
}
