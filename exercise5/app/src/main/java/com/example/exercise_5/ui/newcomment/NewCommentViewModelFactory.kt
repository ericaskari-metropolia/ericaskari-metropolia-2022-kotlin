package com.example.exercise_5.ui.newcomment

import androidx.lifecycle.*
import com.example.exercise_5.ui.membercomment.MemberCommentRepository

/**
 * @author Mohammad Askari
 */
class NewCommentViewModelFactory(private val repository: MemberCommentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewCommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewCommentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
