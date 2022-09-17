package com.example.exercise_5.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exercise_5.datasource.ParliamentMembersData
import com.example.exercise_5.ui.parliamentMember.ParliamentMember

class ParliamentMemberDao {
    private val memberList = mutableListOf<ParliamentMember>()
    private val members = MutableLiveData<List<ParliamentMember>>()

    init {
        members.value = memberList
    }

    fun addMember(member: ParliamentMember) {
        memberList.add(member)
        members.value = memberList
    }

    fun patchMember(index: Int, member: ParliamentMember) {
        memberList[index] = member
        members.value = memberList
    }

    fun getMembers() = members as LiveData<List<ParliamentMember>>
}