package com.example.exercise_5.ui.membergrade

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberGradeRepository(private val apiService: ApiService, private val dao: MemberGradeDao) {
    fun find() = dao.find()

    fun findByHetekaId(hetekaId: Int) = dao.findByHetekaId(hetekaId)

    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: MemberGrade) = dao.insert(*items)

    suspend fun fetch(): List<MemberGrade> = apiService.getMemberGradeList()
}