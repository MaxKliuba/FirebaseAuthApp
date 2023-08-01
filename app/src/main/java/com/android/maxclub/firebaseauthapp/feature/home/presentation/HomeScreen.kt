package com.android.maxclub.firebaseauthapp.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.maxclub.firebaseauthapp.R
import com.android.maxclub.firebaseauthapp.core.model.UserData

@Composable
fun HomeScreen(
    userData: UserData?,
    onClickSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        userData?.userPhotoUrl?.let { userPhotoUrl ->
            AsyncImage(
                model = userPhotoUrl,
                contentDescription = stringResource(R.string.user_photo_text),
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        userData?.userName?.let { userName ->
            Text(
                text = userName,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedButton(
            onClick = onClickSignOut,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_sign_out), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.sing_out_text))
        }
    }
}