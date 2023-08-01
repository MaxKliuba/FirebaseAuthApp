package com.android.maxclub.firebaseauthapp.feature.auth.presentation

import androidx.lifecycle.ViewModel
import com.android.maxclub.firebaseauthapp.feature.auth.data.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInErrorMessage = result.errorMessage
            )
        }
    }

    fun resetState() {
        _uiState.update { SignInUiState() }
    }
}