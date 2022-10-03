package com.example.exercise_5.ui.party

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartyViewModel(private val repository: PartyRepository) : ViewModel() {

    val getAll: LiveData<List<Party>> = repository.getAll()

    private fun insertAll(Partys: List<Party>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(Partys)
    }

    fun populate() {
//        viewModelScope.launch {
//            try {
//                val fetchedPartys: List<Party> = ParliamentApi.parliamentApiService.getParliamentPartys()
//                println("fetchedPartys fetched: ${fetchedPartys.count()}")
//                insertAll(fetchedPartys)
//                println("Parliament Partys have been added.")
//            } catch (e: Exception) {
//                println("Failed to fetch parliament Partys")
//                println(e)
//            }
//        }
    }
}

class PartyViewModelFactory(private val repository: PartyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PartyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
