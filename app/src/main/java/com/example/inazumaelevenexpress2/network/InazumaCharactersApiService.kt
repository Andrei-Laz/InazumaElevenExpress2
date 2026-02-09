package com.example.inazumaelevenexpress2.network

import com.example.inazumaelevenexpress2.model.InazumaCharacter
import retrofit2.http.GET

interface InazumaCharactersApiService {
    @GET("api/inazuma-characters")
    suspend fun getCharacters(): List<InazumaCharacter>
}
