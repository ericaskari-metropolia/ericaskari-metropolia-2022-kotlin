package com.example.exercise_5.ui.parliamentMember

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exercise_5.network.ParliamentApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParliamentMembersViewModel(private val repository: ParliamentMemberRepository) : ViewModel() {

    val getAll: LiveData<List<ParliamentMember>> = repository.getAll()

    private fun insertAll(members: List<ParliamentMember>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(members)
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    fun populateParliamentMembers() {
        viewModelScope.launch {
            try {
                val fetchedMembers: List<ParliamentMember> = ParliamentApi.parliamentApiService.getParliamentMembers()
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

class ParliamentMembersViewModelFactory(private val repository: ParliamentMemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParliamentMembersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
