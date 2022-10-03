package com.example.exercise_5.ui.party

import com.example.exercise_5.network.ApiService

class PartyRepository(private val apiService: ApiService, private val dao: PartyDao) {
    fun getAll() = dao.getAll()

    fun deleteAll() = dao.deleteAll()

    fun insert(members: List<Party>) = dao.insertAll(*members.toTypedArray())
}