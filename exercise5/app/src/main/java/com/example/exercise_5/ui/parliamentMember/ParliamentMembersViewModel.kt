package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.ViewModel

class ParliamentMembersViewModel(private val repository: ParliamentMemberRepository) :
    ViewModel() {
    fun getParliamentMembers() = repository.getParliamentMembers()
    fun addParliamentMember(member: ParliamentMember) = repository.addParliamentMember(member)
    fun patchParliamentMember(index: Int, member: ParliamentMember) = repository.patchParliamentMember(index, member)
}
