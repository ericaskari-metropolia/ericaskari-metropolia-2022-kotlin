package com.example.exercise_5.ui.membercomment

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberCommentRepository(private val apiService: ApiService, private val dao: MemberCommentDao) {
    fun getAll() = dao.getAll()

    fun deleteAll() = dao.deleteAll()

    fun loadAllByMemberId(vararg items: Int) = dao.loadAllByMemberIds(items)

    fun insert(vararg items: MemberComment) = dao.insertAll(*items)

    suspend fun fetch(): List<MemberComment> = apiService.getMemberCommentList()
}