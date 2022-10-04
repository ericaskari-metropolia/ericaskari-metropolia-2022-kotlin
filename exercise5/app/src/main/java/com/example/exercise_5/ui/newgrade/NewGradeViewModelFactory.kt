package com.example.exercise_5.ui.newgrade

import androidx.lifecycle.*
import com.example.exercise_5.ui.membergrade.MemberGrade
import com.example.exercise_5.ui.membergrade.MemberGradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
class NewGradingViewModelFactory(private val repository: MemberGradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewGradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewGradeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
