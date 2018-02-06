package com.meiji.daily.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting

import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.data.local.converter.ZhuanlanBeanConverter
import com.meiji.daily.data.local.dao.ZhuanlanDao

/**
 * Created by Meiji on 2017/11/28.
 */
@Database(entities = [(ZhuanlanBean::class)], version = 1, exportSchema = false)
@TypeConverters(ZhuanlanBeanConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun zhuanlanDao(): ZhuanlanDao

    companion object {

        @VisibleForTesting
        val DATABASE_NAME = "Daily"
        @Volatile
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(context,
                                AppDatabase::class.java,
                                DATABASE_NAME
                        ).build()
                    }
                }
            }
            return sInstance!!
        }
    }
}
