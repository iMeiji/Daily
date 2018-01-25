package com.meiji.daily.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.meiji.daily.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Meiji on 2017/12/28.
 */

@Singleton
public class SettingHelper {

    private SharedPreferences mPreferences;
    private Context mContext;

    @Inject
    public SettingHelper(SharedPreferences preferences, Context context) {
        mPreferences = preferences;
        mContext = context;
    }

    public boolean getIsNoPhotoMode() {
        return mPreferences.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.INSTANCE.isMobileConnected(mContext);
    }

    public int getColor() {
        int defaultColor = mContext.getResources().getColor(R.color.colorPrimary);
        int color = mPreferences.getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    public void setColor(int color) {
        mPreferences.edit().putInt("color", color).apply();
    }

    public boolean getIsNightMode() {
        return mPreferences.getBoolean("switch_nightMode", false);
    }

    public void setIsNightMode(boolean flag) {
        mPreferences.edit().putBoolean("switch_nightMode", flag).apply();
    }

}
