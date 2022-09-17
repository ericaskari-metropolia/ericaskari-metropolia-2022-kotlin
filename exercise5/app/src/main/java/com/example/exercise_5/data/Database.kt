package com.example.exercise_5.data

class Database private constructor() {

    var parliamentMemberDao = ParliamentMemberDao()
        private set


    companion object {
        @Volatile
        private var instance: Database? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Database().also { instance = it }
        }
    }
}