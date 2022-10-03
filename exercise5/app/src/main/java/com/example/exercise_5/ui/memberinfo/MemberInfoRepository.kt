package com.example.exercise_5.ui.memberinfo

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberInfoRepository(private val apiService: ApiService, private val dao: MemberInfoDao) {
    fun getAll() = dao.getAll()

    fun insert(vararg items: MemberInfo) = dao.insertAll(*items)

    suspend fun fetch(): List<MemberInfo> = apiService.getMemberInfoList()

}