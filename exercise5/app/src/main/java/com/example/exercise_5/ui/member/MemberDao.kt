package com.example.exercise_5.ui.member

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Mohammad Askari
 */
@Dao
interface MemberDao {
    @Query("SELECT * FROM Member")
    fun getAll(): LiveData<List<Member>>

    @Query("SELECT * FROM Member WHERE party = :party")
    fun getAllByPartyName(party: String): LiveData<List<Member>>

    @Query("SELECT * FROM Member WHERE hetekaId = :hetekaId")
    fun findOneByHetekaId(hetekaId: Int): LiveData<Member?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Member)

    @Query("DELETE FROM Member")
    fun deleteAll()

    @Query("DELETE FROM Member WHERE hetekaId IN (:hetekaIds)")
    fun deleteMultiple(hetekaIds: IntArray)

    @Delete
    fun delete(user: Member)
}