package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.model.InazumaCharacter
import com.example.inazumaelevenexpress2.network.InazumaCharactersApiService

interface InazumaCharactersRepository {
    suspend fun getCharacters(): List<InazumaCharacter>
}

class DefaultInazumaCharactersRepository(
    private val inazumaCharactersApiService: InazumaCharactersApiService
) : InazumaCharactersRepository {
    override suspend fun getCharacters(): List<InazumaCharacter> = inazumaCharactersApiService.getCharacters()
}