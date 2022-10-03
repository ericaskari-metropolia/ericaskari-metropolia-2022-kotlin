package com.example.exercise_5.ui.party

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Mohammad Askari
 */
@Entity
data class Party(
    @PrimaryKey val name: String,
    @ColumnInfo val seatCount: Int,
)
