package com.meiji.daily.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

import com.meiji.daily.R

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Meiji on 2017/12/28.
 */

@Singleton
class SettingHelper
@Inject
constructor(private val mPreferences: SharedPreferences, private val mContext: Context) {

    val isNoPhotoMode: Boolean
        get() = mPreferences.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.isMobileConnected(mContext)

    var color: Int
        get() {
            val defaultColor = mContext.resources.getColor(R.color.colorPrimary)
            val color = mPreferences.getInt("color", defaultColor)
            return if (color != 0 && Color.alpha(color) != 255) {
                defaultColor
            } else color
        }
        set(color) = mPreferences.edit().putInt("color", color).apply()

    var isNightMode: Boolean
        get() = mPreferences.getBoolean("switch_nightMode", false)
        set(flag) = mPreferences.edit().putBoolean("switch_nightMode", flag).apply()

}
