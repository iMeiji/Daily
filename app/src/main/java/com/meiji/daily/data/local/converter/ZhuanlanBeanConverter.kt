package com.meiji.daily.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.meiji.daily.bean.ZhuanlanBean
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by Meiji on 2017/11/28.
 */

class ZhuanlanBeanConverter {

    private val mMoshi = Moshi.Builder().build()
    private val mCreatorAdapter: JsonAdapter<ZhuanlanBean.Creator>
    private var mAvatarAdapter: JsonAdapter<ZhuanlanBean.Avatar>
    private var mTopicAdapter: JsonAdapter<List<ZhuanlanBean.Topic>>

    init {
        mCreatorAdapter = mMoshi.adapter(ZhuanlanBean.Creator::class.java)
        mAvatarAdapter = mMoshi.adapter(ZhuanlanBean.Avatar::class.java)

        val type = Types.newParameterizedType(List::class.java, ZhuanlanBean.Topic::class.java)
        mTopicAdapter = mMoshi.adapter<List<ZhuanlanBean.Topic>>(type)
    }

    @TypeConverter
    fun fromCreator(c: ZhuanlanBean.Creator): String = mCreatorAdapter.toJson(c)

    @TypeConverter
    fun toCreator(s: String): ZhuanlanBean.Creator = mCreatorAdapter.fromJson(s)!!

    @TypeConverter
    fun fromTopicList(l: List<ZhuanlanBean.Topic>): String = mTopicAdapter.toJson(l)

    @TypeConverter
    fun toTopicList(s: String): List<ZhuanlanBean.Topic> = mTopicAdapter.fromJson(s)!!

    @TypeConverter
    fun fromAvatar(a: ZhuanlanBean.Avatar): String = mAvatarAdapter.toJson(a)

    @TypeConverter
    fun toAvatar(s: String): ZhuanlanBean.Avatar = mAvatarAdapter.fromJson(s)!!
}
