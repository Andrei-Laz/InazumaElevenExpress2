// ui/screens/characters/CharacterDetailsViewModel.kt
package com.example.inazumaelevenexpress2.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inazumaelevenexpress2.InazumaElevenExpressApplication
import com.example.inazumaelevenexpress2.data.CharacterHissatsusRepository
import com.example.inazumaelevenexpress2.model.Hissatsu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface CharacterDetailsUiState {
    object Loading : CharacterDetailsUiState
    data class Success(
        val characterId: Int,
        val assignedHissatsus: List<Hissatsu>
    ) : CharacterDetailsUiState
    data class Error(val message: String) : CharacterDetailsUiState
}

class CharacterDetailsViewModel(
    private val repository: CharacterHissatsusRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailsUiState>(CharacterDetailsUiState.Loading)
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState

    fun loadAssignedHissatsus(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailsUiState.Loading
            val result = repository.getAssignedHissatsus(characterId)
            _uiState.value = if (result.isSuccess) {
                CharacterDetailsUiState.Success(
                    characterId = characterId,
                    assignedHissatsus = result.getOrNull() ?: emptyList()
                )
            } else {
                CharacterDetailsUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun assignHissatsu(characterId: Int, hissatsuId: Int) {
        viewModelScope.launch {
            val result = repository.assignHissatsu(characterId, hissatsuId)
            if (result.isSuccess) {
                loadAssignedHissatsus(characterId) // Refresh list
            }
        }
    }

    fun removeHissatsu(characterId: Int, hissatsuId: Int) {
        viewModelScope.launch {
            val result = repository.removeHissatsu(characterId, hissatsuId)
            if (result.isSuccess) {
                loadAssignedHissatsus(characterId) // Refresh list
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as InazumaElevenExpressApplication)
                val repository = application.container.characterHissatsusRepository
                CharacterDetailsViewModel(repository = repository)
            }
        }
    }
}