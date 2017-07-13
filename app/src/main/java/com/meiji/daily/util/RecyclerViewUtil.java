package com.meiji.daily.util;

import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Meiji on 2017/7/13.
 */

public class RecyclerViewUtil {

    /**
     * 使RecyclerView缓存中在pool中的Item失效
     */
    public static void invalidateCacheItem(RecyclerView recyclerView) {
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(recyclerView));
            RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
