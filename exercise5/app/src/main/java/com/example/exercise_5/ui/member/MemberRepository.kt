package com.example.exercise_5.ui.member

import com.example.exercise_5.network.ApiService

class MemberRepository(private val apiService: ApiService, private val memberDao: MemberDao) {
    fun getAll() = memberDao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = memberDao.deleteMultiple(hetekaIds)

    fun insert(members: List<Member>) = memberDao.insertAll(*members.toTypedArray())
}