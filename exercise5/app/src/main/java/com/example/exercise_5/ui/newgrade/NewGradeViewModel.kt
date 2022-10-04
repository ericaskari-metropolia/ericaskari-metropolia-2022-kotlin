package com.example.exercise_5.ui.newgrade

import androidx.lifecycle.*
import com.example.exercise_5.ui.membergrade.MemberGrade
import com.example.exercise_5.ui.membergrade.MemberGradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Mohammad Askari
 */
class NewGradeViewModel(private val repository: MemberGradeRepository) : ViewModel() {

    fun createNewGrade(username: String, hetekaId: Int, rating: Int, onSuccessListener: () -> Unit) {
        if (isGraded(username, hetekaId).value == true) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(MemberGrade(username, hetekaId, rating))

            viewModelScope.launch {
                onSuccessListener()
            }
        }
    }


    fun currentGrade(username: String, hetekaId: Int): LiveData<Int?> {
        return repository.getAll()
            .map { grades -> grades.filter { grade -> grade.username == username && grade.hetekaId == hetekaId }.firstOrNull()?.grade }
    }

    fun isGraded(username: String, hetekaId: Int): LiveData<Boolean> {
        return currentGrade(username, hetekaId).map { it != null }
    }

}