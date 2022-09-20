package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.exercise_5.ui.parliamentMember.ParliamentMember


//class ParliamentMemberDao {
//    private val memberList = mutableListOf<ParliamentMember>()
//    private val members = MutableLiveData<List<ParliamentMember>>()
//
//    init {
//        members.value = memberList
//    }
//
//    fun addMember(member: ParliamentMember) {
//        memberList.add(member)
//        members.value = memberList
//    }
//
//    fun patchMember(index: Int, member: ParliamentMember) {
//        memberList[index] = member
//        members.value = memberList
//    }
//
//    fun getMembers() = members as LiveData<List<ParliamentMember>>
//}

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

    @Insert
    fun insertAll(vararg users: ParliamentMember)

    @Query("DELETE FROM ParliamentMember")
    fun deleteAll()

    @Delete
    fun delete(user: ParliamentMember)
}