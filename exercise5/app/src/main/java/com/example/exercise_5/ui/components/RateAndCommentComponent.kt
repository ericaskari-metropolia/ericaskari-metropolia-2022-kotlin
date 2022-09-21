package com.example.exercise_5.ui.components

import android.view.View

interface RateAndCommentComponent {
    fun onRateButtonClick(v: View?, index: Int)
    fun onCreateRatingButtonClick(v: View?)
    fun onCommentValueTextChange(value: String)
}