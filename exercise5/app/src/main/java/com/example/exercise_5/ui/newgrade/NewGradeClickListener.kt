package com.example.exercise_5.ui.newgrade

import android.view.View

/**
 * @author Mohammad Askari
 */
interface NewGradeClickListener {
    fun onGradeButtonClick(v: View?, index: Int)
    fun onEditGradeButtonClick(v: View?)
}