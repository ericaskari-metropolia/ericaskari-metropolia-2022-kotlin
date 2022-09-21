package com.example.exercise_5.ui.parliamentMemberRating

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentMemberRatingDao {
    @Query("SELECT * FROM ParliamentMemberRating")
    fun getAll(): LiveData<List<ParliamentMemberRating>>

    @Query("SELECT * FROM ParliamentMemberRating WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<ParliamentMemberRating>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ParliamentMemberRating)

    @Query("DELETE FROM ParliamentMemberRating")
    fun deleteAll()

    @Query("DELETE FROM ParliamentMemberRating WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: ParliamentMemberRating)
}