package com.example.exercise_5.ui.parliamentMember

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParliamentMember(
    @PrimaryKey val hetekaId: Int,
    @ColumnInfo val seatNumber: Int = 0,
    @ColumnInfo val lastname: String,
    @ColumnInfo var firstname: String,
    @ColumnInfo val party: String,
    @ColumnInfo val minister: Boolean = false,
    @ColumnInfo val pictureUrl: String = ""
) {

    fun fullName(): String {
        return this.firstname + ' ' + this.lastname
    }
}

