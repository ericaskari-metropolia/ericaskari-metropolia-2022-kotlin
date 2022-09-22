package com.example.exercise_5.ui.parliamentMember

import com.example.exercise_5.network.ApiService

class ParliamentMemberRepository(private val apiService: ApiService, private val parliamentMemberDao: ParliamentMemberDao) {
    fun getAll() = parliamentMemberDao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = parliamentMemberDao.deleteMultiple(hetekaIds)

    fun insert(members: List<ParliamentMember>) = parliamentMemberDao.insertAll(*members.toTypedArray())
}