package com.example.exercise_5.application

import android.app.Application
import com.example.exercise_5.datasource.AppDatabase
import com.example.exercise_5.network.ApiClient
import com.example.exercise_5.ui.member.MemberRepository
import com.example.exercise_5.ui.memberinfo.MemberInfoRepository
import com.example.exercise_5.ui.membergrade.MemberGradeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ExerciseApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val appDatabase by lazy { AppDatabase.getInstance(this, applicationScope) }

    val memberRepository by lazy { MemberRepository(ApiClient.apiService, appDatabase.parliamentMemberDao()) }
    val memberInfoRepository by lazy { MemberInfoRepository(appDatabase.parliamentMemberInfoDao()) }
    val memberGradeRepository by lazy { MemberGradeRepository(appDatabase.parliamentMemberGradeDao()) }
}