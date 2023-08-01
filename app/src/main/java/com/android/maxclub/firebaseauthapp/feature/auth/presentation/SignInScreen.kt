package com.android.maxclub.firebaseauthapp.feature.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.maxclub.firebaseauthapp.R

@Composable
fun SignInScreen(
    state: SignInUiState,
    onClickSignIn: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInErrorMessage) {
        state.signInErrorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.built_with_firebase_logo),
            contentDescription = stringResource(R.string.build_with_firebase_text),
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        Button(onClick = onClickSignIn) {
            Icon(painter = painterResource(id = R.drawable.ic_sign_in), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.sign_in_text))
        }
    }
}