package com.example.exercise_5.ui.parliamentMemberInfo

class ParliamentMemberInfoRepository(private val dao: ParliamentMemberInfoDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)

    fun insert(members: List<ParliamentMemberInfo>) = dao.insertAll(*members.toTypedArray())
}