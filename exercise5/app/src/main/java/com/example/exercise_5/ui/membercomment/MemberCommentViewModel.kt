package com.example.exercise_5.ui.membercomment

import androidx.lifecycle.*

/**
 * @author Mohammad Askari
 */
class MemberCommentViewModel(private val repository: MemberCommentRepository) : ViewModel() {
    val items: LiveData<List<MemberComment>> = repository.find()

    fun findByHetekaId(id: Int): LiveData<List<MemberComment>> = repository.findByHetekaId(id)
}
