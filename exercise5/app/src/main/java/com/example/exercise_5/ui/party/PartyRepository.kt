package com.example.exercise_5.ui.party

import com.example.exercise_5.network.ApiService

/**
 * @author Mohammad Askari
 */
class PartyRepository(private val apiService: ApiService, private val dao: PartyDao) {
    fun getAll() = dao.getAll()

    fun deleteAll() = dao.deleteAll()

    fun insert(vararg items: Party) = dao.insertAll(*items)

    //  Since there is no endpoint for getting the parties
    //  I used the member list and grouped them by party name
    suspend fun fetch(): List<Party> = apiService.getMemberList()
        .map { it.party }
        .toList()
        .groupingBy { it }
        .eachCount()
        .map { Party(it.key, it.value) }
}