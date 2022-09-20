package com.example.exercise_5.ui.parliamentMember

class ParliamentMemberRepository(private val parliamentMemberDao: ParliamentMemberDao) {
    fun getAll() = parliamentMemberDao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = parliamentMemberDao.deleteMultiple(hetekaIds)

    fun insert(members: List<ParliamentMember>) = parliamentMemberDao.insertAll(*members.toTypedArray())
}