package com.example.inazumaelevenexpress2.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inazumaelevenexpress2.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val username: String) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

@HiltViewModel  // Optional: Use Hilt or manual DI
class AuthViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    // Simulated login (replace with actual API call)
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            // TODO: Replace with actual API call
            if (username == "test" && password == "test") {
                userPreferences.saveToken("mock-jwt-token", username)
                _uiState.value = AuthUiState.Success(username)
            } else {
                _uiState.value = AuthUiState.Error("Invalid credentials")
            }
        }
    }

    // Simulated register (replace with actual API call)
    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            // TODO: Replace with actual API call
            if (password.length >= 6) {
                userPreferences.saveToken("mock-jwt-token", username)
                _uiState.value = AuthUiState.Success(username)
            } else {
                _uiState.value = AuthUiState.Error("Password too short")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearToken()
            _uiState.value = AuthUiState.Idle
        }
    }
}