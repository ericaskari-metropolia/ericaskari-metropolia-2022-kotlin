package com.example.exercise_5.data

import android.app.Application
import com.example.exercise_5.ui.parliamentMember.ParliamentMemberRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ExerciseApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val appDatabase by lazy { AppDatabase.getInstance(this, applicationScope) }
    val parliamentMemberRepository by lazy { ParliamentMemberRepository(appDatabase.parliamentMemberDao()) }
}