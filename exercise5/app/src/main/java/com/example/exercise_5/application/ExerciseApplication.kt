package com.example.exercise_5.application

import android.app.Application
import com.example.exercise_5.datasource.AppDatabase
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberRepository
import com.example.exercise_5.ui.parliamentMemberInfo.ParliamentMemberInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ExerciseApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val appDatabase by lazy { AppDatabase.getInstance(this, applicationScope) }

    val parliamentMemberRepository by lazy { ParliamentMemberRepository(appDatabase.parliamentMemberDao()) }
    val parliamentMemberInfoRepository by lazy { ParliamentMemberInfoRepository(appDatabase.parliamentMemberInfoDao()) }
}