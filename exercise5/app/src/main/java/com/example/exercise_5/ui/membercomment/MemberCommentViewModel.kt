package com.example.exercise_5.ui.membercomment

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
class MemberCommentViewModel(private val repository: MemberCommentRepository) : ViewModel() {
    val getAll: LiveData<List<MemberComment>> = repository.getAll()

    fun loadAllByHetekaId(id: Int): LiveData<List<MemberComment>> = repository.loadAllByHetekaIds(id)


    fun populate() {
        viewModelScope.launch(Dispatchers.IO) {
            val members = repository.fetch()
            repository.insert(*members.toTypedArray())
        }
    }
}
