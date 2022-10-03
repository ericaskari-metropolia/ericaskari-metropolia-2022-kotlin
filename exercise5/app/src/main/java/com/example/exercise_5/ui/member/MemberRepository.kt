package com.example.exercise_5.ui.member

import com.example.exercise_5.network.ApiService

class MemberRepository(private val api: ApiService, private val dao: MemberDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)

    fun insert(members: List<Member>) = dao.insertAll(*members.toTypedArray())

    suspend fun fetch() = api.getParliamentMembers()

}