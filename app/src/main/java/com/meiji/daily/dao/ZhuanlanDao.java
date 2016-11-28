package com.meiji.daily.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.db.ZhuanlanHelper;

import java.util.ArrayList;
import java.util.List;

import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_AVATARId;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_AVATARURL;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_FOLLOWERSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_INTRO;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_SLUG;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_TYPE;
import static com.meiji.daily.db.ZhuanlanHelper.ZHUANLAN_TABLE;

/**
 * Created by Meiji on 2016/11/25.
 */

public class ZhuanlanDao {

    private Context mContext;

    public ZhuanlanDao(Context mContext) {
        this.mContext = mContext;
    }

    public boolean add(
            String type,
            String avatarUrl,
            String avatarId,
            String name,
            String followersCount,
            String postsCount,
            String intro,
            String slug) {
        ZhuanlanHelper helper = new ZhuanlanHelper(mContext, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ZHUANLANBEAN_TYPE, type);
        values.put(ZHUANLANBEAN_AVATARURL, avatarUrl);
        values.put(ZHUANLANBEAN_AVATARId, avatarId);
        values.put(ZHUANLANBEAN_NAME, name);
        values.put(ZHUANLANBEAN_FOLLOWERSCOUNT, followersCount);
        values.put(ZHUANLANBEAN_POSTSCOUNT, postsCount);
        values.put(ZHUANLANBEAN_INTRO, intro);
        values.put(ZHUANLANBEAN_SLUG, slug);
        long id = db.insert(ZHUANLAN_TABLE, null, values);
        db.query(ZHUANLAN_TABLE, null, "slug=?", new String[]{slug}, null, null, null);
        db.close();
        return id != -1;
    }

    public List<ZhuanlanBean> query(int type) {
        ZhuanlanHelper helper = new ZhuanlanHelper(mContext, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ZHUANLAN_TABLE, null, "type=?", new String[]{type + ""}, null, null, null);
        List<ZhuanlanBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ZhuanlanBean bean = new ZhuanlanBean();
            ZhuanlanBean.AvatarBeanX avatarBeanX = new ZhuanlanBean.AvatarBeanX();
            avatarBeanX.setTemplate(cursor.getString(2));
            avatarBeanX.setId(cursor.getString(3));
            bean.setAvatar(avatarBeanX);
            bean.setName(cursor.getString(4));
            bean.setFollowersCount(Integer.parseInt(cursor.getString(5)));
            bean.setPostsCount(Integer.parseInt(cursor.getString(6)));
            bean.setIntro(cursor.getString(7));
            bean.setSlug(cursor.getString(8));
            list.add(bean);
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean addSlug(String slug) {
        ZhuanlanHelper helper = new ZhuanlanHelper(mContext, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ZHUANLANBEAN_SLUG, slug);
        long id = db.insert(ZHUANLAN_TABLE, null, values);
        db.close();
        return id != -1;
    }
}
