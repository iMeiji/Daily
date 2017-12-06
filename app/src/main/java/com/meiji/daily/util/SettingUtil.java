package com.meiji.daily.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.meiji.daily.InitApp;
import com.meiji.daily.R;

/**
 * Created by Meiji on 2017/2/20.
 */

public class SettingUtil {

    private SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(InitApp.sAppContext);

    public static SettingUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    public boolean getIsNoPhotoMode() {
        return setting.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.isMobileConnected(InitApp.sAppContext);
    }

    public int getColor() {
        int defaultColor = InitApp.sAppContext.getResources().getColor(R.color.colorPrimary);
        int color = setting.getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    public void setColor(int color) {
        setting.edit().putInt("color", color).apply();
    }

    public boolean getIsNightMode() {
        return setting.getBoolean("switch_nightMode", false);
    }

    public void setIsNightMode(boolean flag) {
        setting.edit().putBoolean("switch_nightMode", flag).apply();
    }

    private static final class SettingsUtilInstance {
        private static final SettingUtil instance = new SettingUtil();
    }
}
