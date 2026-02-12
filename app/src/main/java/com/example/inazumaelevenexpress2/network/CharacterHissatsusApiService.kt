package com.example.inazumaelevenexpress2.network

import com.example.inazumaelevenexpress2.model.Hissatsu
import retrofit2.Response
import retrofit2.http.*

interface CharacterHissatsusApiService {

    // Get all hissatsus assigned to a character
    @GET("api/character-hissatsus/character/{characterId}")
    suspend fun getAssignedHissatsus(@Path("characterId") characterId: Int): Response<List<Hissatsu>>

    // Assign a hissatsu to a character
    @POST("api/character-hissatsus")
    suspend fun assignHissatsu(
        @Query("characterId") characterId: Int,
        @Query("hissatsuId") hissatsuId: Int
    ): Response<Unit>

    // Remove a hissatsu from a character
    @DELETE("api/character-hissatsus")
    suspend fun removeHissatsu(
        @Query("characterId") characterId: Int,
        @Query("hissatsuId") hissatsuId: Int
    ): Response<Unit>
}