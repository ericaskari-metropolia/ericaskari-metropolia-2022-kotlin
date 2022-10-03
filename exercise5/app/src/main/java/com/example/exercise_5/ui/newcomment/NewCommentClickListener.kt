package com.example.exercise_5.ui.newcomment

import android.view.View

/**
 * @author Mohammad Askari
 */
interface NewCommentClickListener {
    fun onCommentValueTextChange(value: String)
    fun onCreateCommentButtonClicked(v: View?)
}