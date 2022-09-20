package com.example.exercise_5.network

import com.example.exercise_5.ui.parliamentMember.ParliamentMember
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://users.metropolia.fi/~peterh/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ParliamentApiService {
    @GET("seating.json")
    suspend fun getParliamentMembers(): List<ParliamentMember>
}

object ParliamentApi {
    val parliamentApiService: ParliamentApiService by lazy {
        retrofit.create(ParliamentApiService::class.java)
    }
}
