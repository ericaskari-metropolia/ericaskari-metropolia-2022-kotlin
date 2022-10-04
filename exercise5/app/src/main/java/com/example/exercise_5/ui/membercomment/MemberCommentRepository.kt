package com.example.exercise_5.ui.membercomment

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberCommentRepository(private val apiService: ApiService, private val dao: MemberCommentDao) {
    fun find() = dao.find()

    fun findByHetekaId(hetekaId: Int) = dao.findByHetekaId(hetekaId)

    fun insert(vararg items: MemberComment) = dao.insert(*items)

    fun deleteAll() = dao.deleteAll()

    suspend fun fetch(): List<MemberComment> = apiService.getMemberCommentList()
}