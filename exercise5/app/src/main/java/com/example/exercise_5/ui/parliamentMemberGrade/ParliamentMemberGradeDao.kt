package com.example.exercise_5.ui.parliamentMemberGrade

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentMemberGradeDao {
    @Query("SELECT * FROM ParliamentMemberGrade")
    fun getAll(): LiveData<List<ParliamentMemberGrade>>

    @Query("SELECT * FROM ParliamentMemberGrade WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<ParliamentMemberGrade>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ParliamentMemberGrade)

    @Query("DELETE FROM ParliamentMemberGrade")
    fun deleteAll()

    @Query("DELETE FROM ParliamentMemberGrade WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: ParliamentMemberGrade)
}