package com.example.exercise_5.ui.components

import android.view.View

interface NewCommentClickListener {
    fun onCommentValueTextChange(value: String)
    fun onCreateCommentButtonClicked(v: View?)
}