package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberGradeViewModel(private val repository: MemberGradeRepository) : ViewModel() {
    val getAll: LiveData<List<MemberGrade>> = repository.getAll()

    fun createNewRating(hetekaId: Int, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(MemberGrade(0, hetekaId, "anonymous-user", rating))
        }
    }

    fun populate() {
        viewModelScope.launch(Dispatchers.IO) {
            val members = repository.fetch()
            repository.insert(*members.toTypedArray())
        }
    }
}

class MemberGradeViewModelFactory(private val repository: MemberGradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberGradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberGradeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
