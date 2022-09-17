package com.example.exercise_5.ui.parliamentMember

data class ParliamentMember(
    val hetekaId: Int,
    val seatNumber: Int = 0,
    val lastname: String,
    var firstname: String,
    val party: String,
    val minister: Boolean = false,
    val pictureUrl: String = ""
) {

    fun fullName(): String {
        return this.firstname + ' ' + this.lastname
    }
}

