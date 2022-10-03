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
