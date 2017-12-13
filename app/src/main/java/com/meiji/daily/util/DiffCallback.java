package com.meiji.daily.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/6/7.
 */

public class DiffCallback extends DiffUtil.Callback {

    private final Items oldItems, newItems;

    private DiffCallback(Items oldItems, Items newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    public static void create(@NonNull Items oldItems, @NonNull Items newItems, @NonNull MultiTypeAdapter adapter) {
        DiffCallback diffCallback = new DiffCallback(oldItems, newItems);
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
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).hashCode() == newItems.get(newItemPosition).hashCode();
    }
}
