package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.model.Hissatsu
import com.example.inazumaelevenexpress2.model.InazumaCharacter
import com.example.inazumaelevenexpress2.network.HissatsusApiService
import com.example.inazumaelevenexpress2.network.InazumaCharactersApiService


interface HissatsusRepository {
    suspend fun getHissatsus(): List<Hissatsu>
}

class DefaultHissatsusRepository(
    private val hissatsusApiService: HissatsusApiService
) : HissatsusRepository {
    override suspend fun getHissatsus(): List<Hissatsu> = hissatsusApiService.getHissatsus()
}