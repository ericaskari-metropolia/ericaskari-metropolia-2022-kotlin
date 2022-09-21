package com.example.exercise_5.ui.parliamentMemberRating

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParliamentMemberRatingViewModel(private val repository: ParliamentMemberRatingRepository) : ViewModel() {

    val getAll: LiveData<List<ParliamentMemberRating>> = repository.getAll()

    private fun insertAll(members: List<ParliamentMemberRating>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(members)
    }

    private fun deleteMultiple(hetekaIds: IntArray) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMultiple(hetekaIds)
    }

    fun createNewRating(hetekaId: String, rating: Int, comment: String) {

        viewModelScope.launch {
//            try {
//                val response: List<ParliamentMemberVote> = ParliamentApi.parliamentApiService.getParliamentMemberVotes()
//                insertAll(response)
//                println("Parliament member info have been added.")
//            } catch (e: Exception) {
//                println("Failed to fetch parliament member info")
//                println(e)
//            }
        }
    }
}

class ParliamentMemberRatingViewModelFactory(private val repository: ParliamentMemberRatingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMemberRatingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParliamentMemberRatingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
