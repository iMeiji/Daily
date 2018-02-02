package com.meiji.daily.binder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.meiji.daily.GlideApp
import com.meiji.daily.R
import com.meiji.daily.bean.PostsListBean
import com.meiji.daily.module.postscontent.PostsContentActivity
import kotlinx.android.synthetic.main.item_postlist.view.*
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by Meiji on 2017/6/6.
 */

internal class PostsListViewBinder : ItemViewBinder<PostsListBean, PostsListViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): PostsListViewBinder.ViewHolder {
        val view = inflater.inflate(R.layout.item_postlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsListViewBinder.ViewHolder, item: PostsListBean) {
        val context = holder.itemView.context
        val publishedTime = item.publishedTime.substring(0, 10)
        val likesCount = item.likesCount.toString() + "赞"
        val commentsCount = item.commentsCount.toString() + "条评论"
        var titleImage = item.titleImage
        val title = item.title

        if (!TextUtils.isEmpty(titleImage)) {
            titleImage = item.titleImage.replace("r.jpg", "b.jpg")
            GlideApp.with(context)
                    .load(titleImage)
                    .centerCrop()
                    .error(R.color.viewBackground)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.iv_titleImage)
        } else {
            holder.iv_titleImage.setImageResource(R.drawable.error_image)
            holder.iv_titleImage.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        holder.tv_publishedTime.text = publishedTime
        holder.tv_likesCount.text = likesCount
        holder.tv_commentsCount.text = commentsCount
        holder.tv_title.text = title
        holder.root.setOnClickListener { PostsContentActivity.start(context, item.titleImage, item.title, item.slug) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val root: CardView = itemView.ll_root
        val tv_publishedTime: TextView = itemView.tv_publishedTime
        val tv_likesCount: TextView = itemView.tv_likesCount
        val tv_commentsCount: TextView = itemView.tv_commentsCount
        val iv_titleImage: ImageView = itemView.iv_titleImage
        val tv_title: TextView = itemView.tv_title

    }
}
