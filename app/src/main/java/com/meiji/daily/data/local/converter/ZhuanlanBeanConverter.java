package com.meiji.daily.data.local.converter;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiji.daily.bean.ZhuanlanBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2017/11/28.
 */

public class ZhuanlanBeanConverter {

    private static Gson mGson = new Gson();

    @TypeConverter
    public static String toString(@NonNull ZhuanlanBean.AvatarBeanX avatarBeanX) {
        return mGson.toJson(avatarBeanX);
    }

    @TypeConverter
    public static ZhuanlanBean.AvatarBeanX toBean(@NonNull String s) {
        return mGson.fromJson(s, ZhuanlanBean.AvatarBeanX.class);
    }

    public static List<ZhuanlanBean.AvatarBeanX> toList(@NonNull String s) {
        Type listType = new TypeToken<ArrayList<ZhuanlanBean.AvatarBeanX>>() {
        }.getType();
        return mGson.fromJson(s, listType);
    }
}
