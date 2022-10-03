package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberGradeDao {
    @Query("SELECT * FROM MemberGrade")
    fun getAll(): LiveData<List<MemberGrade>>

    @Query("SELECT * FROM MemberGrade WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<MemberGrade>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: MemberGrade)

    @Query("DELETE FROM MemberGrade")
    fun deleteAll()

    @Query("DELETE FROM MemberGrade WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: MemberGrade)
}