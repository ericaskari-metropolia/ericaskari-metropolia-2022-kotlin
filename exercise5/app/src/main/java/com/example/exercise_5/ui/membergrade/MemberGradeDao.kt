package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberGradeDao {
    @Query("SELECT * FROM MemberGrade")
    fun find(): LiveData<List<MemberGrade>>

    @Query("SELECT * FROM MemberGrade WHERE hetekaId = :hetekaId")
    fun findByHetekaId(hetekaId: Int): LiveData<List<MemberGrade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: MemberGrade)

    @Delete
    fun delete(user: MemberGrade)

    @Query("DELETE FROM MemberGrade WHERE hetekaId IN (:hetekaIds)")
    fun deleteByHetekaIds(hetekaIds: IntArray)

    @Query("DELETE FROM MemberGrade")
    fun deleteAll()
}