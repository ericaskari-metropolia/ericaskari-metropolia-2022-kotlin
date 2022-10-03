package com.example.exercise_5.ui.memberinfo

class MemberInfoRepository(private val dao: MemberInfoDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)

    fun insert(members: List<MemberInfo>) = dao.insertAll(*members.toTypedArray())
}