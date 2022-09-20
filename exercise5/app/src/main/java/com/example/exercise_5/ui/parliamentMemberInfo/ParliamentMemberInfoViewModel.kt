package com.example.exercise_5.ui.parliamentMemberInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exercise_5.network.ParliamentApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParliamentMemberInfoViewModel(private val repository: ParliamentMemberInfoRepository) : ViewModel() {

    val getAll: LiveData<List<ParliamentMemberInfo>> = repository.getAll()

    private fun insertAll(members: List<ParliamentMemberInfo>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(members)
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    fun populate() {
        viewModelScope.launch {
            try {
                val response: List<ParliamentMemberInfo> = ParliamentApi.parliamentApiService.getParliamentMemberInfos()
                insertAll(response)
                println("Parliament member info have been added.")
            } catch (e: Exception) {
                println("Failed to fetch parliament member info")
                println(e)
            }
        }
    }
}

class ParliamentMemberInfoViewModelFactory(private val repository: ParliamentMemberInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMemberInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParliamentMemberInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
