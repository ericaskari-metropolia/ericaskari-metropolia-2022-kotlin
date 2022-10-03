package com.example.exercise_5.ui.member

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberRepository(private val api: ApiService, private val dao: MemberDao) {
    fun getAll() = dao.getAll()

    fun getAllByPartyName(party: String) = dao.getAllByPartyName(party)

    fun findOneByHetekaId(id: Int) = dao.findOneByHetekaId(id)

    fun insert(vararg items: Member) = dao.insertAll(*items)

    suspend fun fetch(): List<Member> = api.getMemberList()

}