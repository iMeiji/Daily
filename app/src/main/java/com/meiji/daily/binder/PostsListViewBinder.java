package com.meiji.daily.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meiji.daily.R;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.module.postscontent.PostsContentActivity;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/6.
 */

public class PostsListViewBinder extends ItemViewBinder<PostsListBean, PostsListViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected PostsListViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_postlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostsListViewBinder.ViewHolder holder, @NonNull final PostsListBean item) {
        final Context context = holder.itemView.getContext();
        String publishedTime = item.getPublishedTime().substring(0, 10);
        String likesCount = item.getLikesCount() + "赞";
        String commentsCount = item.getCommentsCount() + "条评论";
        String titleImage = item.getTitleImage();
        String title = item.getTitle();

        if (!TextUtils.isEmpty(titleImage)) {
            titleImage = item.getTitleImage().replace("r.jpg", "b.jpg");
            Glide.with(context)
                    .asBitmap()
                    .apply(new RequestOptions().centerCrop())
                    .load(titleImage)
                    .into(holder.iv_titleImage);
        } else {
            holder.iv_titleImage.setImageResource(R.drawable.error_image);
            holder.iv_titleImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.tv_publishedTime.setText(publishedTime);
        holder.tv_likesCount.setText(likesCount);
        holder.tv_commentsCount.setText(commentsCount);
        holder.tv_title.setText(title);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostsContentActivity.start(context, item.getTitleImage(), item.getTitle(), item.getSlug());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView root;
        private TextView tv_publishedTime;
        private TextView tv_likesCount;
        private TextView tv_commentsCount;
        private ImageView iv_titleImage;
        private TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.tv_publishedTime = itemView.findViewById(R.id.tv_publishedTime);
            this.tv_likesCount = itemView.findViewById(R.id.tv_likesCount);
            this.tv_commentsCount = itemView.findViewById(R.id.tv_commentsCount);
            this.iv_titleImage = itemView.findViewById(R.id.iv_titleImage);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
