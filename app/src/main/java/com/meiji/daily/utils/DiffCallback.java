package com.meiji.daily.utils;

import android.support.v7.util.DiffUtil;

import com.meiji.daily.bean.PostsListBean;
import com.meiji.daily.bean.ZhuanlanBean;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/7.
 */

public class DiffCallback extends DiffUtil.Callback {

    public static final int POSTSLIST = 0;
    public static final int ZHUANLAN = 1;
    private Items oldItems, newItems;
    private int type;

    public DiffCallback(Items oldItems, Items newItems, int type) {
        this.oldItems = oldItems;
        this.newItems = newItems;
        this.type = type;
    }

    public static void create(Items oldItems, Items newItems, int type, MultiTypeAdapter adapter) {
        DiffCallback diffCallback = new DiffCallback(oldItems, newItems, type);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public int getOldListSize() {
        return oldItems != null ? oldItems.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newItems != null ? newItems.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            if (type == POSTSLIST) {
                boolean equals = ((PostsListBean) oldItems.get(oldItemPosition)).getTitle()
                        .equals(((PostsListBean) newItems.get(newItemPosition)).getTitle());
                return equals;
            }
            if (type == ZHUANLAN) {
                boolean equals = ((ZhuanlanBean) oldItems.get(oldItemPosition)).getName()
                        .equals(((ZhuanlanBean) newItems.get(newItemPosition)).getName());
                return equals;
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            if (type == POSTSLIST) {
                boolean equals = ((PostsListBean) oldItems.get(oldItemPosition)).getContent()
                        .equals(((PostsListBean) newItems.get(newItemPosition)).getContent());
                return equals;
            }
            if (type == ZHUANLAN) {
                boolean equals = ((ZhuanlanBean) oldItems.get(oldItemPosition)).getIntro()
                        .equals(((ZhuanlanBean) newItems.get(newItemPosition)).getIntro());
                return equals;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }
}
