package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParliamentMembersViewModelFactory(private val repository: ParliamentMemberRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ParliamentMembersViewModel(repository) as T
    }
}