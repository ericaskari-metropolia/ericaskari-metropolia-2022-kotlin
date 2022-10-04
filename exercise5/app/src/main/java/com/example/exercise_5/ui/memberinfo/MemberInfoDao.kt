package com.example.exercise_5.ui.memberinfo

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberInfoDao {
    @Query("SELECT * FROM MemberInfo")
    fun find(): LiveData<List<MemberInfo>>

    @Query("SELECT * FROM MemberInfo WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<MemberInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: MemberInfo)

    @Query("DELETE FROM MemberInfo")
    fun deleteAll()

    @Query("DELETE FROM MemberInfo WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: MemberInfo)
}