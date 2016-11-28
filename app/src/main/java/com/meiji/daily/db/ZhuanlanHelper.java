package com.meiji.daily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_AVATARId;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_AVATARURL;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_FOLLOWERSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_INTRO;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_SLUG;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_TYPE;

/**
 * Created by Meiji on 2016/11/25.
 */

public class ZhuanlanHelper extends SQLiteOpenHelper {

    public static final String ZHUANLAN_TABLE = "zhuanlan";
    private static final String ZHUANLAN_BDNAME = "zhuanlan.db";
    private static String createTable = "create table zhuanlan" +
            "(" +
            "id integer," +
            ZHUANLANBEAN_TYPE + " varcher(10)," +
            ZHUANLANBEAN_AVATARURL + " varcher(50)," +
            ZHUANLANBEAN_AVATARId + " varcher(30)," +
            ZHUANLANBEAN_NAME + " varcher(20)," +
            ZHUANLANBEAN_FOLLOWERSCOUNT + " varcher(10)," +
            ZHUANLANBEAN_POSTSCOUNT + " varcher(10)," +
            ZHUANLANBEAN_INTRO + " varcher(30)," +
            ZHUANLANBEAN_SLUG + " varcher(20) primary key" +
            ")";

    public ZhuanlanHelper(Context context, int version) {
        super(context, ZHUANLAN_BDNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
