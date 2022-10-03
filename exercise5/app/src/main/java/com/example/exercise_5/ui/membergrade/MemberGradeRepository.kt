package com.example.exercise_5.ui.membergrade

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberGradeRepository(private val apiService: ApiService, private val dao: MemberGradeDao) {
    fun getAll() = dao.getAll()

    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: MemberGrade) = dao.insertAll(*items)

    suspend fun fetch(): List<MemberGrade> = apiService.getMemberGradeList()
}