package com.example.exercise_5.network

/**
 * @author Mohammad Askari
 */
object ImageApiClient {
    private const val BASE_URL = "https://avoindata.eduskunta.fi"

    fun imageUrlBuilder(imageId: String?): String? {
        if (imageId == null) {
            return null
        }
        return "$BASE_URL/$imageId"
    }
}