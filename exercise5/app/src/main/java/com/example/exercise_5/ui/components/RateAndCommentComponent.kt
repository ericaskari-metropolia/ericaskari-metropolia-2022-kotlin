package com.example.exercise_5.ui.components

import android.view.View

interface RateAndCommentComponent {
    var commentValue: String
    var ratingValue: Int
    fun onRateButtonClick(v: View?, index: Int)
    fun onCreateRatingButtonClick(v: View?)

}