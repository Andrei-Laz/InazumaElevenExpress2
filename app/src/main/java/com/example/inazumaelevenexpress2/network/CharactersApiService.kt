package com.example.inazumaelevenexpress2.network

import retrofit2.http.GET

interface CharactersApiService {
    @GET("amphibians")
    suspend fun getCharacters(): List<Character>
}
