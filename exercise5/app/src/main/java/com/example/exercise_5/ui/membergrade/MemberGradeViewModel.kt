package com.example.exercise_5.ui.membergrade

import androidx.lifecycle.*
import java.math.RoundingMode

/**
 * @author Mohammad Askari
 */
class MemberGradeViewModel(private val repository: MemberGradeRepository) : ViewModel() {
    val items: LiveData<List<MemberGrade>> = repository.find()

    /**
     * One decimal precision grade value
     */
    fun getGradeValue(hetekaId: Int) = repository.findByHetekaId(hetekaId).map { allRatings ->
        val sum = allRatings.sumOf { it.grade }
        val average: Float = if (allRatings.isEmpty()) 0F else (sum.toFloat() / allRatings.count())
        return@map average.toBigDecimal().setScale(1, RoundingMode.HALF_UP)
    }

    /**
     * grade value in integer format
     */
    fun getRoundedGrade(userId: Int) = getGradeValue(userId).map {
        return@map it.setScale(0, RoundingMode.HALF_UP).toInt()
    }

    fun getGradeCount(hetekaId: Int) = repository.findByHetekaId(hetekaId).map { allRatings ->
        val all = allRatings.map { it.grade }
        return@map all.count()
    }
}