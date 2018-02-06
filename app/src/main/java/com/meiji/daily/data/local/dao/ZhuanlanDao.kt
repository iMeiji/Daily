package com.meiji.daily.data.local.dao

/**
 * Created by Meiji on 2017/11/28.
 */

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.meiji.daily.bean.ZhuanlanBean

import io.reactivex.Maybe

@Dao
interface ZhuanlanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(zhuanlanBean: ZhuanlanBean): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: MutableList<ZhuanlanBean>)

    @Query("SELECT * FROM zhuanlans WHERE type = :type")
    fun query(type: Int): Maybe<MutableList<ZhuanlanBean>>

    @Query("DELETE FROM zhuanlans WHERE slug = :slug")
    fun delete(slug: String)
}
