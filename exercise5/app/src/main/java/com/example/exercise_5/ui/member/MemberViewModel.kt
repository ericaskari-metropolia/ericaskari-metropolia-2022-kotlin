package com.example.exercise_5.ui.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
class MemberViewModel(private val repository: MemberRepository) : ViewModel() {
    val getAll: LiveData<List<Member>> = repository.getAll()

    fun getAllByPartyName(party: String): LiveData<List<Member>> = repository.getAllByPartyName(party)

    fun findOneByHetekaId(id: Int): LiveData<Member?> = repository.findOneByHetekaId(id)

    fun populate() {
        viewModelScope.launch(Dispatchers.IO) {
            val members = repository.fetch()
            repository.insert(*members.toTypedArray())
        }
    }
}
