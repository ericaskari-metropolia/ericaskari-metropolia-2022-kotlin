package com.example.exercise_5.ui.membergrade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberGrade(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val hetekaId: Int,
    @ColumnInfo val userId: String,
    @ColumnInfo val grade: Int,
) {
}


