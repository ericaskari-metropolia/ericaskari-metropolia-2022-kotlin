package com.example.exercise_5.ui.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exercise_5.network.ParliamentApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(private val repository: MemberRepository) : ViewModel() {

    val getAll: LiveData<List<Member>> = repository.getAll()

    private fun insertAll(members: List<Member>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(members)
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    fun populate() {
        viewModelScope.launch {
            try {
                val fetchedMembers: List<Member> = ParliamentApi.parliamentApiService.getParliamentMembers()
                println("fetchedMembers fetched: ${fetchedMembers.count()}")
                insertAll(fetchedMembers)
                println("Parliament members have been added.")
            } catch (e: Exception) {
                println("Failed to fetch parliament members")
                println(e)
            }
        }
    }
}

class MemberViewModelFactory(private val repository: MemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
