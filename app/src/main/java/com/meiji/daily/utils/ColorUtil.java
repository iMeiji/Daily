package com.meiji.daily.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Meiji on 2016/11/26.
 */

public class ColorUtil {

    public static void setColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        prefs.edit().putInt("color", color).apply();
    }

    public static int getColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        int defaultColor = -14776091;
        return prefs.getInt("color", defaultColor);
    }
}
