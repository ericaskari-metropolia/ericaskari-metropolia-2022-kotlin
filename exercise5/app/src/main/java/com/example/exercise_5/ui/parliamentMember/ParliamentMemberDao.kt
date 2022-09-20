package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentMemberDao {
    @Query("SELECT * FROM ParliamentMember")
    fun getAll(): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM ParliamentMember WHERE hetekaId IN (:hetekaIds)")
    fun loadAllByIds(hetekaIds: IntArray): List<ParliamentMember>

    @Query(
        "SELECT * FROM ParliamentMember WHERE firstname LIKE :first AND " +
                "lastname LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): ParliamentMember

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ParliamentMember)

    @Query("DELETE FROM ParliamentMember")
    fun deleteAll()

    @Query("DELETE FROM ParliamentMember WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: ParliamentMember)
}