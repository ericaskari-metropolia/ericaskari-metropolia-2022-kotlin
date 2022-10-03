package com.example.exercise_5.ui.memberinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberInfoViewModel(private val repository: MemberInfoRepository) : ViewModel() {

    val getAll: LiveData<List<MemberInfo>> = repository.getAll()

    private fun insertAll(members: List<MemberInfo>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(members)
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    fun populate() {
        viewModelScope.launch {
            insertAll(repository.fetch())
        }
    }
}

class MemberInfoViewModelFactory(private val repository: MemberInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
