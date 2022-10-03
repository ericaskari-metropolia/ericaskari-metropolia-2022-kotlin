package com.example.exercise_5.ui.newcomment

import androidx.lifecycle.*
import com.example.exercise_5.ui.membercomment.MemberComment
import com.example.exercise_5.ui.membercomment.MemberCommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Mohammad Askari
 */
class NewCommentViewModel(private val repository: MemberCommentRepository) : ViewModel() {

    fun createNewComment(username: String, hetekaId: Int, comment: String, onSuccessListener: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(MemberComment(0, username, hetekaId, Date(), comment))

            viewModelScope.launch {
                onSuccessListener()
            }
        }
    }
}

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
