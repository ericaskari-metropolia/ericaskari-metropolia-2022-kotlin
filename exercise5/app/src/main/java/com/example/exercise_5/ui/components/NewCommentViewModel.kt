package com.example.exercise_5.ui.components

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCommentViewModel : ViewModel() {
    val commentValue: MutableLiveData<String> by lazy { MutableLiveData<String>() }


    init {
        println("NewCommentViewModel INSTANCE CREATED")
    }

    fun updateCommentValue(value: String) {
        commentValue.postValue(value)
    }

}

class NewCommentViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewCommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewCommentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
