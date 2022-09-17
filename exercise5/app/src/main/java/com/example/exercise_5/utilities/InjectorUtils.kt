package com.example.exercise_5.utilities

import com.example.exercise_5.data.Database
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberRepository
import com.example.exercise_5.ui.parliamentMember.ParliamentMembersViewModelFactory

object InjectorUtils {
    fun provideParliamentMembersViewModelFactory(): ParliamentMembersViewModelFactory {
        val repository = ParliamentMemberRepository.getInstance(Database.getInstance().parliamentMemberDao)
        return ParliamentMembersViewModelFactory(repository)
    }
}