package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.*

/**
 * @author Mohammad Askari
 */
class MemberGradeViewModelFactory(private val repository: MemberGradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberGradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberGradeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
