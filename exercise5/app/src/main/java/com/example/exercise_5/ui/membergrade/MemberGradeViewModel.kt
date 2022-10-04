package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode

/**
 * @author Mohammad Askari
 */
class MemberGradeViewModel(private val repository: MemberGradeRepository) : ViewModel() {
    val getAll: LiveData<List<MemberGrade>> = repository.getAll()

    /**
     * One decimal precision grade value
     */
    fun getGradeValue(userId: Int) = getAll.map { allRatings ->
        val all = allRatings.filter { it.hetekaId == userId }.map { it.grade }
        val sum = all.sum()
        val average: Float = if (all.isEmpty()) 0F else (sum.toFloat() / all.count())
        return@map average.toBigDecimal().setScale(1, RoundingMode.HALF_UP)
    }

    /**
     * grade value in integer format
     */
    fun getRoundedGrade(userId: Int) = getGradeValue(userId).map {
        return@map it.setScale(0, RoundingMode.HALF_UP).toInt()
    }

    fun getGradeCount(userId: Int) = getAll.map { allRatings ->
        val all = allRatings.filter { it.hetekaId == userId }.map { it.grade }
        return@map all.count()
    }


    fun populate() {
        viewModelScope.launch(Dispatchers.IO) {
            val members = repository.fetch()
            repository.insert(*members.toTypedArray())
        }
    }
}