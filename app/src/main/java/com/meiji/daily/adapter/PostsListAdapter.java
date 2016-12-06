package com.meiji.daily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.daily.R;
import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.interfaces.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/11/20.
 */

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostsListItemViewHolder> {

    private List<PostsListBean> list = new ArrayList<>();
    private Context mContext;
    private IOnItemClickListener listener;

    public PostsListAdapter(List<PostsListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PostsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_postlist, parent, false);
        return new PostsListItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(PostsListItemViewHolder holder, int position) {
        PostsListBean bean = list.get(position);
        String publishedTime = bean.getPublishedTime().substring(0, 10);
        String likesCount = bean.getLikesCount() + "赞";
        String commentsCount = bean.getCommentsCount() + "条评论";
        String titleImage = bean.getTitleImage();
        String title = bean.getTitle();

        if (!TextUtils.isEmpty(titleImage)) {
            titleImage = bean.getTitleImage().replace("r.jpg", "b.jpg");
            Glide.with(mContext).load(titleImage).asBitmap().centerCrop().into(holder.iv_titleImage);
        } else {
            holder.iv_titleImage.setImageResource(R.drawable.error_image);
            holder.iv_titleImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.tv_publishedTime.setText(publishedTime);
        holder.tv_likesCount.setText(likesCount);
        holder.tv_commentsCount.setText(commentsCount);
        holder.tv_title.setText(title);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class PostsListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_publishedTime;
        private TextView tv_likesCount;
        private TextView tv_commentsCount;
        private ImageView iv_titleImage;
        private TextView tv_title;
        private IOnItemClickListener listener;

        public PostsListItemViewHolder(View itemView, IOnItemClickListener listener) {
            super(itemView);
            this.tv_publishedTime = (TextView) itemView.findViewById(R.id.tv_publishedTime);
            this.tv_likesCount = (TextView) itemView.findViewById(R.id.tv_likesCount);
            this.tv_commentsCount = (TextView) itemView.findViewById(R.id.tv_commentsCount);
            this.iv_titleImage = (ImageView) itemView.findViewById(R.id.iv_titleImage);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick(view, getLayoutPosition());
            }
        }
    }
}


