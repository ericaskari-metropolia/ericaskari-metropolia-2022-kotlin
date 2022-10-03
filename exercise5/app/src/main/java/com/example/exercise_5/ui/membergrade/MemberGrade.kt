package com.example.exercise_5.ui.membergrade

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * @author Mohammad Askari
 */
@Entity(primaryKeys = ["username", "hetekaId"])
data class MemberGrade(
    @ColumnInfo val username: String,
    @ColumnInfo val hetekaId: Int,
    @ColumnInfo val grade: Int,
) {
}


