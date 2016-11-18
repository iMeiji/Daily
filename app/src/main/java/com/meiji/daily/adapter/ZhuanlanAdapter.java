package com.meiji.daily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Meiji on 2016/11/16.
 */
public class ZhuanlanAdapter extends RecyclerView.Adapter<ZhuanlanAdapter.ZhuanlanItemViewHolder> {

    private Context mContext;
    private List<ZhuanlanBean> list = new ArrayList<>();
    private IOnItemClickListener mListener;

    public ZhuanlanAdapter(Context context, List<ZhuanlanBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setItemClickListener(IOnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ZhuanlanItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zhuanlan_item, parent, false);
        return new ZhuanlanItemViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ZhuanlanItemViewHolder holder, int position) {
        ZhuanlanBean bean = list.get(position);
        String followersCount = bean.getFollowersCount() + "人关注TA";
        String postsCount = bean.getPostsCount() + "篇文章";
        String avatarUrl = bean.getAvatar().getTemplate();
        if (avatarUrl != null) {
            // 拼凑avatar链接
            avatarUrl = avatarUrl
                    .replace("{id}", bean.getAvatar().getId())
                    .replace("{size}", "m");
        }
        Glide.with(mContext).load(avatarUrl).asBitmap().into(holder.cv_avatar);
        holder.tv_name.setText(bean.getName());
        holder.tv_followersCount.setText(followersCount);
        holder.tv_postsCount.setText(postsCount);
        holder.tv_intro.setText(bean.getIntro());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ZhuanlanItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView cv_avatar;
        private TextView tv_name;
        private TextView tv_followersCount;
        private TextView tv_postsCount;
        private TextView tv_intro;
        private IOnItemClickListener mListener;

        public ZhuanlanItemViewHolder(View itemView, IOnItemClickListener mListener) {
            super(itemView);
            cv_avatar = (CircleImageView) itemView.findViewById(R.id.cv_avatar);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_followersCount = (TextView) itemView.findViewById(R.id.tv_followersCount);
            tv_postsCount = (TextView) itemView.findViewById(R.id.tv_postsCount);
            tv_intro = (TextView) itemView.findViewById(R.id.tv_intro);
            this.mListener = mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onClick(view, getLayoutPosition());
            }
        }
    }

}
