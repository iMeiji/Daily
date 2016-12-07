package com.meiji.daily.database.table;

/**
 * Created by Meiji on 2016/12/7.
 */

public class ZhuanlanTable {

    /**
     * 专栏信息表
     */
    public static final String TABLENAME = "ZhuanlanTable";

    /**
     * 字段部分
     */
    public static final String TYPE = "type";
    public static final String AVATARURL = "avatarUrl";
    public static final String AVATARId = "avatarId";
    public static final String NAME = "name";
    public static final String FOLLOWERSCOUNT = "followersCount";
    public static final String POSTSCOUNT = "postsCount";
    public static final String INTRO = "intro";
    public static final String SLUG = "slug";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_TYPE = 0;
    public static final int ID_AVATARURL = 1;
    public static final int ID_AVATARId = 2;
    public static final int ID_NAME = 3;
    public static final int ID_FOLLOWERSCOUNT = 4;
    public static final int ID_POSTSCOUNT = 5;
    public static final int ID_INTRO = 6;
    public static final int ID_SLUG = 7;

    public static final String CREATE_TABLE = "create table " + TABLENAME + "(" +
            TYPE + " text," +
            AVATARURL + " text," +
            AVATARId + " text," +
            NAME + " text," +
            FOLLOWERSCOUNT + " text," +
            POSTSCOUNT + " text," +
            INTRO + " text," +
            SLUG + " text primary key) ";
}
