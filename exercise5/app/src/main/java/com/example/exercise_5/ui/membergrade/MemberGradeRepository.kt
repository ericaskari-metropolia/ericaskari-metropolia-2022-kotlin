package com.example.exercise_5.ui.membergrade

import com.example.exercise_5.network.ApiService
import com.example.exercise_5.ui.memberinfo.MemberInfo

class MemberGradeRepository(private val apiService: ApiService, private val dao: MemberGradeDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)
    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: MemberGrade) = dao.insertAll(*items)

    suspend fun fetch(): List<MemberGrade> = apiService.getMemberGradeList()
}