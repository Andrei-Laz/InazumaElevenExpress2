package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.network.CharacterHissatsusApiService
import com.example.inazumaelevenexpress2.network.HissatsusApiService
import com.example.inazumaelevenexpress2.network.InazumaCharactersApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val inazumaCharactersRepository: InazumaCharactersRepository
    val hissatsusRepository: HissatsusRepository
    val characterHissatsusRepository: CharacterHissatsusRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory((Json.asConverterFactory("application/json".toMediaType())))
        .baseUrl(BASE_URL)
        .build()

    private val inazumaCharactersRetrofitService: InazumaCharactersApiService by lazy {
        retrofit.create(InazumaCharactersApiService::class.java)
    }
    override val inazumaCharactersRepository: InazumaCharactersRepository by lazy {
        DefaultInazumaCharactersRepository(inazumaCharactersRetrofitService)
    }

    private val hissatsusRetrofitService: HissatsusApiService by lazy {
        retrofit.create(HissatsusApiService::class.java)
    }
    override val hissatsusRepository: HissatsusRepository by lazy {
        DefaultHissatsusRepository(hissatsusRetrofitService)
    }

    private val characterHissatsusService: CharacterHissatsusApiService by lazy {
        retrofit.create(CharacterHissatsusApiService::class.java)
    }
    override val characterHissatsusRepository: CharacterHissatsusRepository by lazy {
        DefaultCharacterHissatsusRepository(characterHissatsusService)
    }
}