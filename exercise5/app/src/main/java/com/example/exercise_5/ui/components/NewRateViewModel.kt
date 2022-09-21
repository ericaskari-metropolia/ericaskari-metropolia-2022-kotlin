package com.example.exercise_5.ui.components

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewRatingViewModel : ViewModel() {
    val ratingValue: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }


    init {
        println("NewRatingViewModel INSTANCE CREATED")
    }

    fun updateRatingValue(value: Int) {
        ratingValue.postValue(value)
    }


}

class NewRatingViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewRatingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewRatingViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
