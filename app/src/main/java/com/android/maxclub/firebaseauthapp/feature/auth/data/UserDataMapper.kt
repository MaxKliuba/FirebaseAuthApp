package com.android.maxclub.firebaseauthapp.feature.auth.data

import com.android.maxclub.firebaseauthapp.core.model.UserData
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserData(): UserData =
    UserData(
        userId = this.uid,
        userName = this.displayName,
        userPhotoUrl = this.photoUrl?.toString()
    )