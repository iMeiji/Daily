package com.meiji.daily.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.local.converter.ZhuanlanBeanConverter;
import com.meiji.daily.data.local.dao.ZhuanlanDao;

/**
 * Created by Meiji on 2017/11/28.
 */
@Database(entities = {ZhuanlanBean.class},
        version = 1,
        exportSchema = false)
@TypeConverters({ZhuanlanBeanConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "Daily";
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context,
                            AppDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return sInstance;
    }

    public abstract ZhuanlanDao ZhuanlanNewDao();
}
