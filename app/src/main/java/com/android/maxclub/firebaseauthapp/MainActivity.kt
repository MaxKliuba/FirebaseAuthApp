package com.android.maxclub.firebaseauthapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.maxclub.firebaseauthapp.core.util.Screen
import com.android.maxclub.firebaseauthapp.feature.auth.data.GoogleAuthClient
import com.android.maxclub.firebaseauthapp.feature.auth.presentation.SignInScreen
import com.android.maxclub.firebaseauthapp.feature.auth.presentation.SignInViewModel
import com.android.maxclub.firebaseauthapp.feature.home.presentation.HomeScreen
import com.android.maxclub.firebaseauthapp.ui.theme.FirebaseAuthAppTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuthClient(Identity.getSignInClient(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val startDestination = if (googleAuthClient.signedInUser == null) {
                        Screen.SignInScreen.route
                    } else {
                        Screen.HomeScreen.route
                    }

                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                    ) {
                        composable(route = Screen.SignInScreen.route) {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.uiState.collectAsStateWithLifecycle()

                            val launcherForSignInIntentResult = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == Activity.RESULT_OK) {
                                        lifecycleScope.launch {
                                            result.data?.let { intent ->
                                                val signInResult =
                                                    googleAuthClient.signInWithIntent(intent)
                                                viewModel.onSignInResult(signInResult)
                                            }
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    navController.navigate(Screen.HomeScreen.route) {
                                        popUpTo(Screen.SignInScreen.route) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onClickSignIn = {
                                    lifecycleScope.launch {
                                        googleAuthClient.signIn()?.let { intentSender ->
                                            launcherForSignInIntentResult.launch(
                                                IntentSenderRequest.Builder(intentSender).build()
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(
                                userData = googleAuthClient.signedInUser,
                                onClickSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthClient.signOut()

                                        navController.navigate(Screen.SignInScreen.route) {
                                            popUpTo(Screen.HomeScreen.route) {
                                                inclusive = true
                                                saveState = false
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}