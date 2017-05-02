package com.meiji.daily.utils;

import android.content.SharedPreferences;

import com.meiji.daily.InitApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Meiji on 2016/11/26.
 */

public class ColorUtils {

    public static int getColor() {
        SharedPreferences prefs = InitApp.AppContext.getSharedPreferences(InitApp.AppContext.getPackageName(), MODE_PRIVATE);
        int defaultColor = -14776091;
        return prefs.getInt("color", defaultColor);
    }

    public static void setColor(int color) {
        SharedPreferences prefs = InitApp.AppContext.getSharedPreferences(InitApp.AppContext.getPackageName(), MODE_PRIVATE);
        prefs.edit().putInt("color", color).apply();
    }
}
