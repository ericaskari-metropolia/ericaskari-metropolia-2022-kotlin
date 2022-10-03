package com.example.exercise_5.ui.membercomment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Mohammad Askari
 */
@Entity
data class MemberComment(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val username: String,
    @ColumnInfo val hetekaId: Int,
    @ColumnInfo val createdAt: Date,
    @ColumnInfo val comment: String,
) {
}
