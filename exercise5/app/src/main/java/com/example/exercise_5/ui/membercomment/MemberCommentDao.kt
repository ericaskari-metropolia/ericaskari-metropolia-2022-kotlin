package com.example.exercise_5.ui.membercomment

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberCommentDao {
    @Query("SELECT * FROM MemberComment")
    fun getAll(): LiveData<List<MemberComment>>

    @Query("SELECT * FROM MemberComment WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<MemberComment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: MemberComment)

    @Query("DELETE FROM MemberComment")
    fun deleteAll()

    @Query("DELETE FROM MemberComment WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: MemberComment)
}