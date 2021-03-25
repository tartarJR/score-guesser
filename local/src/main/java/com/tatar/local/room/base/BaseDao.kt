package com.tatar.local.room.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>): Completable

    @Update
    fun update(obj: T): Completable

    @Delete
    fun delete(obj: T): Completable
}

