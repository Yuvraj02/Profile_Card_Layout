package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profilecardlayout.ui.theme.LightGreen
import com.example.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.example.profilecardlayout.ui.theme.UserProfile
import com.example.profilecardlayout.ui.theme.profiles


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(topBar = { AppBar() }) {
        androidx.compose.material.Surface(modifier = Modifier.fillMaxSize()) {
            Column() {
                for (profile in profiles)
                ProfileCard(profile)
            }

        }
    }
}

@Composable
fun ProfileCard(userProfile: UserProfile) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.drawableID,userProfile.status)
            ProfileContent(userProfile.name,userProfile.status)
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(navigationIcon = {
        Icon(
            Icons.Default.Home,
            contentDescription = "Home Icon",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    },
        title = { Text(text = "Messenger") })

}


@Composable
fun ProfilePicture(drawableID: Int,status: Boolean) {

    Card(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = if (status) LightGreen else Color.Red),
        modifier = Modifier.padding(16.dp), elevation = 4.dp,

        ) {
        Image(
            painter = painterResource(id = drawableID),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun ProfileContent(name: String, status: Boolean) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {


        CompositionLocalProvider(LocalContentAlpha provides if(status) 1f else ContentAlpha.medium) {
            Text(text = name, style = MaterialTheme.typography.h5)
            Text(text = if (status) "Active Now" else "Offline", style = MaterialTheme.typography.body2)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardLayoutTheme() {
        MainScreen()
    }
}