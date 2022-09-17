package com.example.exercise_5.ui.parliamentMember

import com.example.exercise_5.data.ParliamentMemberDao

class ParliamentMemberRepository private constructor(private val parliamentMemberDao: ParliamentMemberDao) {

    fun addParliamentMember(member: ParliamentMember) {
        parliamentMemberDao.addMember(member)
    }

    fun patchParliamentMember(index: Int, member: ParliamentMember) {
        parliamentMemberDao.patchMember(index, member)
    }

    fun getParliamentMembers() = parliamentMemberDao.getMembers()

    companion object {
        @Volatile
        private var instance: ParliamentMemberRepository? = null

        fun getInstance(parliamentMemberDao: ParliamentMemberDao) = instance ?: synchronized(this) {
            instance ?: ParliamentMemberRepository(parliamentMemberDao).also { instance = it }
        }
    }
}