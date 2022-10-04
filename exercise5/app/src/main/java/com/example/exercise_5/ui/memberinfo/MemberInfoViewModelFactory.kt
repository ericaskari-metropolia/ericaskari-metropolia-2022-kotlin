package com.example.exercise_5.ui.memberinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Mohammad Askari
 */
class MemberInfoViewModelFactory(private val repository: MemberInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
