package com.example.inazumaelevenexpress2.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inazumaelevenexpress2.InazumaElevenExpressApplication
import com.example.inazumaelevenexpress2.data.InazumaCharactersRepository
import com.example.inazumaelevenexpress2.model.InazumaCharacter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface InazumaCharactersUiState {
    data class Success(val characters: List<InazumaCharacter>) : InazumaCharactersUiState
    object Error : InazumaCharactersUiState
    object Loading : InazumaCharactersUiState
}

class InazumaCharactersViewModel(private val inazumaCharactersRepository: InazumaCharactersRepository) : ViewModel() {
    var inazumaCharactersUiState: InazumaCharactersUiState by mutableStateOf(InazumaCharactersUiState.Loading)
        private set

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            inazumaCharactersUiState = InazumaCharactersUiState.Loading
            inazumaCharactersUiState = try {
                InazumaCharactersUiState.Success(inazumaCharactersRepository.getCharacters())
            } catch (e: IOException) {
                InazumaCharactersUiState.Error
            } catch (e: HttpException) {
                InazumaCharactersUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as InazumaElevenExpressApplication)
                val charactersRepository = application.container.inazumaCharactersRepository
                InazumaCharactersViewModel(inazumaCharactersRepository = charactersRepository)
            }
        }
    }
}