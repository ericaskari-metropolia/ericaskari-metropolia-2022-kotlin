package com.example.exercise_5.ui.party

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PartyDao {
    @Query("SELECT * FROM Party")
    fun getAll(): LiveData<List<Party>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Party)

    @Query("DELETE FROM Party")
    fun deleteAll()

    @Delete
    fun delete(item: Party)
}