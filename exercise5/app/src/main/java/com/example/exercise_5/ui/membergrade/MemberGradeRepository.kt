package com.example.exercise_5.ui.membergrade

class MemberGradeRepository(private val dao: MemberGradeDao) {
    fun getAll() = dao.getAll()

    fun deleteMultiple(hetekaIds: IntArray) = dao.deleteMultiple(hetekaIds)
    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: MemberGrade) = dao.insertAll(*items)
}