package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ParliamentMembersViewModel(private val repository: ParliamentMemberRepository) : ViewModel() {

    val getAll: LiveData<List<ParliamentMember>> = repository.getAll()

    fun insertAll(members: List<ParliamentMember>) = viewModelScope.launch {
        repository.insert(members)
    }
}

class ParliamentMembersViewModelFactory(private val repository: ParliamentMemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParliamentMembersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
