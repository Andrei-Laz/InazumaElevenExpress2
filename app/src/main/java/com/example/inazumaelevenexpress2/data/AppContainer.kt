package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.network.InazumaCharactersApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val inazumaCharactersRepository: InazumaCharactersRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory((Json.asConverterFactory("application/json".toMediaType())))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: InazumaCharactersApiService by lazy {
        retrofit.create(InazumaCharactersApiService::class.java)
    }

    override val inazumaCharactersRepository: InazumaCharactersRepository by lazy {
        DefaultInazumaCharactersRepository(retrofitService)
    }
}