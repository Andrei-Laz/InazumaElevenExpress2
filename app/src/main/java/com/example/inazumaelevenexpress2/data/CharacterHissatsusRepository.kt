package com.example.inazumaelevenexpress2.data

import com.example.inazumaelevenexpress2.model.Hissatsu
import com.example.inazumaelevenexpress2.network.CharacterHissatsusApiService
import retrofit2.HttpException
import java.io.IOException

interface CharacterHissatsusRepository {
    suspend fun getAssignedHissatsus(characterId: Int): Result<List<Hissatsu>>
    suspend fun assignHissatsu(characterId: Int, hissatsuId: Int): Result<Unit>
    suspend fun removeHissatsu(characterId: Int, hissatsuId: Int): Result<Unit>
}

class DefaultCharacterHissatsusRepository(
    private val apiService: CharacterHissatsusApiService
) : CharacterHissatsusRepository {

    override suspend fun getAssignedHissatsus(characterId: Int): Result<List<Hissatsu>> {
        return try {
            val response = apiService.getAssignedHissatsus(characterId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun assignHissatsu(characterId: Int, hissatsuId: Int): Result<Unit> {
        return try {
            val response = apiService.assignHissatsu(characterId, hissatsuId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun removeHissatsu(characterId: Int, hissatsuId: Int): Result<Unit> {
        return try {
            val response = apiService.removeHissatsu(characterId, hissatsuId)
            if (response.isSuccessful || response.code() == 204) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}