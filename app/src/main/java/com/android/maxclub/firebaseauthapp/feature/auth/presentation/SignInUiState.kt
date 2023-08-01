package com.android.maxclub.firebaseauthapp.feature.auth.presentation

data class SignInUiState(
    val isSignInSuccessful: Boolean = false,
    val signInErrorMessage: String? = null,
)
