package com.meiji.daily.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.module.postslist.PostsListActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Meiji on 2017/6/6.
 */

public class ZhuanlanViewBinder extends ItemViewBinder<ZhuanlanBean, ZhuanlanViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ZhuanlanViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_zhuanlan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ZhuanlanViewBinder.ViewHolder holder, @NonNull final ZhuanlanBean item) {
        final Context context = holder.itemView.getContext();
        String followersCount = item.getFollowersCount() + "人关注TA";
        String postsCount = item.getPostsCount() + "篇文章";
        String avatarUrl = item.getAvatar().getTemplate();
        if (!TextUtils.isEmpty(avatarUrl)) {
            // 拼凑avatar链接
            avatarUrl = avatarUrl
                    .replace("{id}", item.getAvatar().getId())
                    .replace("{size}", "m");
            Glide.with(context)
                    .asBitmap()
                    .apply(new RequestOptions().centerCrop())
                    .load(avatarUrl)
                    .into(holder.cv_avatar);
        }
        holder.tv_name.setText(item.getName());
        holder.tv_followersCount.setText(followersCount);
        holder.tv_postsCount.setText(postsCount);
        holder.tv_intro.setText(item.getIntro());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostsListActivity.start(context, item.getSlug(), item.getName(), item.getPostsCount());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private CircleImageView cv_avatar;
        private TextView tv_name;
        private TextView tv_followersCount;
        private TextView tv_postsCount;
        private TextView tv_intro;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            cv_avatar = itemView.findViewById(R.id.cv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_followersCount = itemView.findViewById(R.id.tv_followersCount);
            tv_postsCount = itemView.findViewById(R.id.tv_postsCount);
            tv_intro = itemView.findViewById(R.id.tv_intro);
        }
    }
}
