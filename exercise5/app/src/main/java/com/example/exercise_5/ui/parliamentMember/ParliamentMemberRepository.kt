package com.example.exercise_5.ui.parliamentMember

import androidx.annotation.WorkerThread

class ParliamentMemberRepository(private val parliamentMemberDao: ParliamentMemberDao) {

    fun addParliamentMembers(members: List<ParliamentMember>) {
        parliamentMemberDao.insertAll(*members.toTypedArray())
    }

    fun getAll() = parliamentMemberDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(members: List<ParliamentMember>) {
        parliamentMemberDao.insertAll(*members.toTypedArray())
    }
}