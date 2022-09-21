package com.example.exercise_5.ui.parliamentMemberRating

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParliamentMemberRating(
    @PrimaryKey val id: Int,
    @ColumnInfo val hetekaId: Int,
    @ColumnInfo val userId: String,
    @ColumnInfo val vote: Int,
    @ColumnInfo val comment: String,
) {
}


