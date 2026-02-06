package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.network.CharactersApiService

interface CharactersRepository {
    suspend fun getCharacters(): List<Character>
}

class DefaultCharactersRepository(
    private val charactersApiService: CharactersApiService
) : CharactersRepository {
    override suspend fun getCharacters(): List<Character> = charactersApiService.getCharacters()
}