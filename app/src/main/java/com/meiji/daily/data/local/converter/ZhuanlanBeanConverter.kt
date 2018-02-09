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
    fun fromCreator(c: ZhuanlanBean.Creator): String {
        return mGson.toJson(c)
    }

    @TypeConverter
    fun toCreator(s: String): ZhuanlanBean.Creator {
        return mGson.fromJson(s, ZhuanlanBean.Creator::class.java)
    }

    @TypeConverter
    fun fromTopicList(l: List<ZhuanlanBean.Topic>): String {
        return mGson.toJson(l)
    }

    @TypeConverter
    fun toTopicList(s: String): List<ZhuanlanBean.Topic> {
        val listType = object : TypeToken<ArrayList<ZhuanlanBean.Topic>>() {}.type
        return mGson.fromJson(s, listType)
    }

    @TypeConverter
    fun fromAvatar(a: ZhuanlanBean.Avatar): String {
        return mGson.toJson(a)
    }

    @TypeConverter
    fun toAvatar(s: String): ZhuanlanBean.Avatar {
        return mGson.fromJson(s, ZhuanlanBean.Avatar::class.java)
    }
}
