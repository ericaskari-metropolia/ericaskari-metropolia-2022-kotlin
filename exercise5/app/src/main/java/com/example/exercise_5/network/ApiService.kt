package com.example.exercise_5.network

import com.example.exercise_5.ui.parliamentMember.ParliamentMember
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfo
import retrofit2.http.GET

interface ApiService {
    @GET("seating.json")
    suspend fun getParliamentMembers(): List<ParliamentMember>

    @GET("extras.json")
    suspend fun getParliamentMemberInfos(): List<ParliamentMemberInfo>
}