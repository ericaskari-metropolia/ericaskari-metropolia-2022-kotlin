package com.example.exercise_5.ui.memberinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
class MemberInfoViewModel(private val repository: MemberInfoRepository) : ViewModel() {
    val getAll: LiveData<List<MemberInfo>> = repository.getAll()

    fun populate() {
        viewModelScope.launch(Dispatchers.IO) {
            val members = repository.fetch()
            repository.insert(*members.toTypedArray())
        }
    }
}