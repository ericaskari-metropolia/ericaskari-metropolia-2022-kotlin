package com.example.exercise_5.ui.parliamentMemberGrade

class ParliamentMemberGradeRepository(private val dao: ParliamentMemberGradeDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)
    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: ParliamentMemberGrade) = dao.insertAll(*items)
}