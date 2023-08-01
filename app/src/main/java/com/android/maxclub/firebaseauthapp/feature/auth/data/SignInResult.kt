package com.android.maxclub.firebaseauthapp.feature.auth.data

import com.android.maxclub.firebaseauthapp.core.model.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?,
)
