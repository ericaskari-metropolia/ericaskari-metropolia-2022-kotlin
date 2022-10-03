package com.example.exercise_5.network

import com.example.exercise_5.ui.member.Member
import com.example.exercise_5.ui.memberinfo.MemberInfo
import retrofit2.http.GET

interface ApiService {
    @GET("seating.json")
    suspend fun getParliamentMembers(): List<Member>

    @GET("extras.json")
    suspend fun getParliamentMemberInfos(): List<MemberInfo>
}