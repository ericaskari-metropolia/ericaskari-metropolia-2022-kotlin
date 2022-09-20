package com.example.exercise_5.ui.parliamentMember

import androidx.annotation.WorkerThread

class ParliamentMemberRepository(private val parliamentMemberDao: ParliamentMemberDao) {

    fun addParliamentMembers(members: List<ParliamentMember>) {
        parliamentMemberDao.insertAll(*members.toTypedArray())
    }

    fun getAll() = parliamentMemberDao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = parliamentMemberDao.deleteMultiple(hetekaIds)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(members: List<ParliamentMember>) {
        parliamentMemberDao.insertAll(*members.toTypedArray())
    }
}