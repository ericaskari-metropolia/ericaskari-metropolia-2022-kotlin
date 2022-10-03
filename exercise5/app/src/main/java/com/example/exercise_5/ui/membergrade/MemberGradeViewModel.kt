package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberGradeViewModel(private val repository: MemberGradeRepository) : ViewModel() {

    val getAll: LiveData<List<MemberGrade>> = repository.getAll()

    private fun insert(member: MemberGrade) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(member)
    }

    private fun insertAll(members: List<MemberGrade>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(*members.toTypedArray())
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    private fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun createNewRating(hetekaId: Int, rating: Int) {
        viewModelScope.launch {
            try {
                val grade = MemberGrade(0, hetekaId, "anonymous-user", rating)
                insert(grade)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun deleteAllGrades() {
        viewModelScope.launch {
            try {
                deleteAll()
            } catch (e: Exception) {
                println(e)
            }
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
