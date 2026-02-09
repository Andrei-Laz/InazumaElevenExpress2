package com.example.inazumaelevenexpress2.network

import com.example.inazumaelevenexpress2.model.Hissatsu
import retrofit2.http.GET

interface HissatsusApiService {
    @GET("api/hissatsus")
    suspend fun getHissatsus(): List<Hissatsu>
}