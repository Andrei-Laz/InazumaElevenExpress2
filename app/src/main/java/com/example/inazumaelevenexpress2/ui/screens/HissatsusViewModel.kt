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
import com.example.inazumaelevenexpress2.data.HissatsusRepository
import com.example.inazumaelevenexpress2.model.Hissatsu
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HissatsusUiState {
    data class Success(val hissatsus: List<Hissatsu>) : HissatsusUiState
    object Error: HissatsusUiState
    object Loading : HissatsusUiState
}

class HissatsusViewModel(private val hissatsusRepository: HissatsusRepository) : ViewModel() {
    var hissatsusUiState: HissatsusUiState by mutableStateOf(HissatsusUiState.Loading)
        private set

    init {
        getHissatsus()
    }

    fun getHissatsus() {
        viewModelScope.launch {
            hissatsusUiState = HissatsusUiState.Loading
            hissatsusUiState = try {
                HissatsusUiState.Success(hissatsusRepository.getHissatsus())
            } catch (e: IOException) {
                HissatsusUiState.Error
            } catch (e: HttpException) {
                HissatsusUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as InazumaElevenExpressApplication)
                val hissatsusRepository = application.container.hissatsusRepository
                HissatsusViewModel(hissatsusRepository = hissatsusRepository)
            }
        }
    }
}