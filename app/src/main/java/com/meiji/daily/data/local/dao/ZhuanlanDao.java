package com.meiji.daily.data.local.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.local.DatabaseHelper;
import com.meiji.daily.data.local.table.ZhuanlanTable;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Meiji on 2016/11/25.
 */
@Deprecated
public class ZhuanlanDao {

    private SQLiteDatabase db;

    public ZhuanlanDao() {
        this.db = DatabaseHelper.getDatabase();
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
        ContentValues values = new ContentValues();
        values.put(ZhuanlanTable.TYPE, type);
        values.put(ZhuanlanTable.AVATARURL, avatarUrl);
        values.put(ZhuanlanTable.AVATARId, avatarId);
        values.put(ZhuanlanTable.NAME, name);
        values.put(ZhuanlanTable.FOLLOWERSCOUNT, followersCount);
        values.put(ZhuanlanTable.POSTSCOUNT, postsCount);
        values.put(ZhuanlanTable.INTRO, intro);
        values.put(ZhuanlanTable.SLUG, slug);
        long result = db.insert(ZhuanlanTable.TABLENAME, null, values);
        return result != -1;
    }

    public boolean add(int type, ZhuanlanBean bean) {
        boolean result = false;
        try {
            String type_ = String.valueOf(type);
            String avatarUrl = bean.getAvatar().getTemplate();
            String avatarId = bean.getAvatar().getId();
            String name = bean.getName();
            String followersCount = String.valueOf(bean.getFollowersCount());
            String postsCount = String.valueOf(bean.getPostsCount());
            String intro = bean.getIntro();
            String slug = bean.getSlug();
            result = add(type_, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ZhuanlanBean> query(int type) {
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        Cursor cursor = db.query(ZhuanlanTable.TABLENAME, null, "type=?", new String[]{type + ""}, null, null, null);
        List<ZhuanlanBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ZhuanlanBean bean = new ZhuanlanBean();
            ZhuanlanBean.AvatarBeanX avatarBeanX = new ZhuanlanBean.AvatarBeanX();
            avatarBeanX.setTemplate(cursor.getString(ZhuanlanTable.ID_AVATARURL));
            avatarBeanX.setId(cursor.getString(ZhuanlanTable.ID_AVATARId));
            bean.setAvatar(avatarBeanX);
            bean.setName(cursor.getString(ZhuanlanTable.ID_NAME));
            bean.setFollowersCount(Integer.parseInt(cursor.getString(ZhuanlanTable.ID_FOLLOWERSCOUNT)));
            bean.setPostsCount(Integer.parseInt(cursor.getString(ZhuanlanTable.ID_POSTSCOUNT)));
            bean.setIntro(cursor.getString(ZhuanlanTable.ID_INTRO));
            bean.setSlug(cursor.getString(ZhuanlanTable.ID_SLUG));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public boolean removeSlug(String slug) {
        SQLiteDatabase db = DatabaseHelper.getDatabase();
        db.delete(ZhuanlanTable.TABLENAME, "slug=?", new String[]{slug});
        return id != -1;
    }
}
