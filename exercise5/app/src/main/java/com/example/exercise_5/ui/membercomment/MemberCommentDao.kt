package com.example.exercise_5.ui.membercomment

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberCommentDao {
    @Query("SELECT * FROM MemberComment")
    fun find(): LiveData<List<MemberComment>>

    @Query("SELECT * FROM MemberComment WHERE hetekaId = :hetekaId")
    fun findByHetekaId(hetekaId: Int): LiveData<List<MemberComment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: MemberComment)

    @Delete
    fun delete(user: MemberComment)

    @Query("DELETE FROM MemberComment WHERE hetekaId IN (:hetekaIds)")
    fun deleteByHetekaIds(hetekaIds: IntArray)

    @Query("DELETE FROM MemberComment")
    fun deleteAll()
}