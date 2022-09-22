package com.example.exercise_5.ui.components

import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*

class NewGradeViewModel : ViewModel() {
    val ratingValue: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }


    init {
        println("NewRatingViewModel INSTANCE CREATED")
    }

    fun updateRatingValue(value: Int) {
        ratingValue.postValue(value)
    }

    fun onRateButtonClick(v: View?, index: Int) {
        //  TODO: Check if User already rated and update the current value if it exists.
        updateRatingValue(index)
    }

}

class NewRatingViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewGradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewGradeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
