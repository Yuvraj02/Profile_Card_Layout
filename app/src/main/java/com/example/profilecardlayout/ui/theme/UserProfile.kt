package com.example.profilecardlayout.ui.theme

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import com.example.profilecardlayout.R

data class UserProfile(val name: String,val status:Boolean, val drawableID: Int)

val profiles: List<UserProfile> =
    listOf(UserProfile("Rashi", true, R.drawable.profile_picture),
        UserProfile("Yuvraj",false, R.drawable.profile2),
    UserProfile("Nishchay",false,R.drawable.ic_launcher_background))