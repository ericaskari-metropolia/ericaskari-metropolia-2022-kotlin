package com.example.exercise_5.ui.member

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class MemberRepository(private val api: ApiService, private val dao: MemberDao) {
    fun find() = dao.find()

    fun findByPartyName(party: String) = dao.findByPartyName(party)

    fun findOneByHetekaId(id: Int) = dao.findOneByHetekaId(id)

    fun insert(vararg items: Member) = dao.insert(*items)

    suspend fun fetch(): List<Member> = api.getMemberList()

}