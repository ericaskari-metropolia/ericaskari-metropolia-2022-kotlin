package com.example.exercise_5.ui.memberinfo

import com.example.exercise_5.network.ApiService

class MemberInfoRepository(private val apiService: ApiService, private val dao: MemberInfoDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)

    fun insert(members: List<MemberInfo>) = dao.insertAll(*members.toTypedArray())

    suspend fun fetch() = apiService.getParliamentMemberInfos()

}