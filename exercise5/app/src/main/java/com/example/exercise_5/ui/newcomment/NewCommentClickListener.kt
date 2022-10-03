package com.example.exercise_5.ui.newcomment

import android.view.View

interface NewCommentClickListener {
    fun onCommentValueTextChange(value: String)
    fun onCreateCommentButtonClicked(v: View?)
}