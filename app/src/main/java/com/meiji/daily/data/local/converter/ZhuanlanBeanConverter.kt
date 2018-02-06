package com.meiji.daily.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meiji.daily.bean.ZhuanlanBean
import java.util.*

/**
 * Created by Meiji on 2017/11/28.
 */

class ZhuanlanBeanConverter {

    private val mGson = Gson()

    @TypeConverter
    fun toString(avatarBeanX: ZhuanlanBean.AvatarBeanX): String {
        return mGson.toJson(avatarBeanX)
    }

    @TypeConverter
    fun toBean(s: String): ZhuanlanBean.AvatarBeanX {
        return mGson.fromJson(s, ZhuanlanBean.AvatarBeanX::class.java)
    }

    fun toList(s: String): List<ZhuanlanBean.AvatarBeanX> {
        val listType = object : TypeToken<ArrayList<ZhuanlanBean.AvatarBeanX>>() {

        }.type
        return mGson.fromJson(s, listType)
    }
}
