package com.example.exercise_5.ui.membercomment

import androidx.lifecycle.*

/**
 * @author Mohammad Askari
 */
class MemberCommentViewModelFactory(private val repository: MemberCommentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberCommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberCommentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
