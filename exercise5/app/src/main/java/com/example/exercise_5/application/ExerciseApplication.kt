package com.example.exercise_5.application

import android.app.Application
import com.example.exercise_5.datasource.AppDatabase
import com.example.exercise_5.network.ApiClient
import com.example.exercise_5.ui.member.MemberRepository
import com.example.exercise_5.ui.membercomment.MemberCommentRepository
import com.example.exercise_5.ui.memberinfo.MemberInfoRepository
import com.example.exercise_5.ui.membergrade.MemberGradeRepository
import com.example.exercise_5.ui.party.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


/**
 * @author Mohammad Askari
 */
class ExerciseApplication : Application() {
    private var _username: String? = null

    fun login(username: String) {
        if (_username == null) {
            this._username = username
        } else {
            //  Say logout first.
        }
    }

    fun username(): String = this._username!!

    //  App Scope
    private val applicationScope = CoroutineScope(SupervisorJob())

    //  App Database
    private val appDatabase by lazy { AppDatabase.getInstance(this, applicationScope) }

    //  App Repositories
    val memberRepository by lazy { MemberRepository(ApiClient.apiService, appDatabase.memberDao()) }
    val memberInfoRepository by lazy { MemberInfoRepository(ApiClient.apiService, appDatabase.memberInfoDao()) }
    val memberGradeRepository by lazy { MemberGradeRepository(ApiClient.apiService, appDatabase.memberGradeDao()) }
    val memberCommentRepository by lazy { MemberCommentRepository(ApiClient.apiService, appDatabase.memberCommentDao()) }
    val partyRepository by lazy { PartyRepository(ApiClient.apiService, appDatabase.partyDao()) }
}