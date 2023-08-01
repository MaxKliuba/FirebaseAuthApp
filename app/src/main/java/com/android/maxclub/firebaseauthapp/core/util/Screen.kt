package com.android.maxclub.firebaseauthapp.core.util

sealed class Screen(val route: String) {
    object SignInScreen : Screen("sign_in")
    object HomeScreen : Screen("home")
}
