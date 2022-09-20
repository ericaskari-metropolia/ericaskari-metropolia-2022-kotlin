package com.example.exercise_5.ui.parliamentMemberInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParliamentMemberInfo(
    @PrimaryKey val hetekaId: Int,
    @ColumnInfo val twitter: String,
    @ColumnInfo var bornYear: Int,
    @ColumnInfo val constituency: String,
) {
}


