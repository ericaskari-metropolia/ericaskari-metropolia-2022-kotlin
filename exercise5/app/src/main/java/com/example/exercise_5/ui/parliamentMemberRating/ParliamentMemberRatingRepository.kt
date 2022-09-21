package com.example.exercise_5.ui.parliamentMemberRating

class ParliamentMemberRatingRepository(private val dao: ParliamentMemberRatingDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)

    fun insert(members: List<ParliamentMemberRating>) = dao.insertAll(*members.toTypedArray())
}