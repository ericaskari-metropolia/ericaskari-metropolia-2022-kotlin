package com.example.exercise_5.ui.parliamentMemberInfo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentMemberInfoDao {
    @Query("SELECT * FROM ParliamentMemberInfo")
    fun getAll(): LiveData<List<ParliamentMemberInfo>>

    @Query("SELECT * FROM ParliamentMemberInfo WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<ParliamentMemberInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ParliamentMemberInfo)

    @Query("DELETE FROM ParliamentMemberInfo")
    fun deleteAll()

    @Query("DELETE FROM ParliamentMemberInfo WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: ParliamentMemberInfo)
}