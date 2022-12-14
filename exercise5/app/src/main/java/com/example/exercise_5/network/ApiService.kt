package com.example.exercise_5.network

import com.example.exercise_5.ui.member.Member
import com.example.exercise_5.ui.membercomment.MemberComment
import com.example.exercise_5.ui.membergrade.MemberGrade
import com.example.exercise_5.ui.memberinfo.MemberInfo
import retrofit2.http.GET

/**
 * @author Mohammad Askari
 * Available remote data sources
 */
interface ApiService {
    @GET("seating.json")
    suspend fun getMemberList(): List<Member>

    @GET("extras.json")
    suspend fun getMemberInfoList(): List<MemberInfo>

    //  No remote data to fetch
    suspend fun getMemberGradeList(): List<MemberGrade> = listOf()

    //  No remote data to fetch
    suspend fun getMemberCommentList(): List<MemberComment> = listOf()
}