package com.example.exercise_5.ui.memberinfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberInfo(
    @PrimaryKey val hetekaId: Int,
    @ColumnInfo val twitter: String,
    @ColumnInfo var bornYear: Int,
    @ColumnInfo val constituency: String,
) {
}


