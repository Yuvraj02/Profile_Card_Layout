package com.example.profilecardlayout

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.profilecardlayout.ui.theme.LightGreen
import com.example.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.example.profilecardlayout.ui.theme.UserProfile
import com.example.profilecardlayout.ui.theme.profiles


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                com.example.profilecardlayout.Application()
            }
        }
    }
}

@Composable
fun Application(userProfile: List<UserProfile> = profiles) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list") {
        composable("users_list") {
            MainScreen(userProfile = userProfile, navController)
        }
        composable("user_deets/{userID}", arguments = listOf(navArgument("userID") {
            type = NavType.IntType
        })) { navBackStackEntry ->
            UserProfileDetailsScreen(navBackStackEntry.arguments!!.getInt("userID"),navController)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MainScreen(userProfile: List<UserProfile>, navHostController: NavHostController?) {
    Scaffold(topBar = { AppBar("Messenger",image = Icons.Default.Home){

    } }) {
        Surface(modifier = Modifier.fillMaxSize()) {

            LazyColumn() {
                items(userProfile) { profile ->
                    ProfileCard(userProfile = profile) {
                        navHostController?.navigate("user_deets/${profile.id}")
                    }

                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { clickAction.invoke() },
        elevation = 8.dp,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.imageUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, alignment = Alignment.Start)
        }
    }
}

@Composable
fun AppBar(title:String,image:ImageVector,clickAction: () -> Unit) {
    TopAppBar(navigationIcon = {
        Icon(
           image,
            contentDescription = "Home Icon",
            modifier = Modifier.padding(horizontal = 16.dp).clickable { clickAction.invoke() }
        )
    },
        title = { Text(text = title) })

}


@ExperimentalCoilApi
@Composable
fun ProfilePicture(imageUrl: String, status: Boolean, imageSize: Dp) {

    Card(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = if (status) LightGreen else Color.Red),
        modifier = Modifier.padding(16.dp), elevation = 4.dp,

        ) {

        Image(
            painter = rememberImagePainter(data = imageUrl, builder = {
                transformations(CircleCropTransformation())
            }),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(imageSize),
            //contentScale = ContentScale.Crop

        )
    }

}

@Composable
fun ProfileContent(name: String, status: Boolean, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
//            .fillMaxWidth()
        horizontalAlignment = alignment
    ) {


        CompositionLocalProvider(LocalContentAlpha provides if (status) 1f else ContentAlpha.medium) {
            Text(text = name, style = MaterialTheme.typography.h5)
            Text(
                text = if (status) "Active Now" else "Offline",
                style = MaterialTheme.typography.body2
            )
        }
    }

}

@Composable
fun UserProfileDetailsScreen(userID: Int,navHostController: NavHostController?) {
    val userProfile = profiles.first{userProfile -> userID == userProfile.id }
    Scaffold(topBar = { AppBar("User Details",Icons.Default.ArrowBack){
        navHostController?.navigateUp()
    } }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(userProfile.imageUrl, userProfile.status, 240.dp)
                ProfileContent(userProfile.name, userProfile.status, Alignment.CenterHorizontally)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardLayoutTheme {
        MainScreen(userProfile = profiles, navHostController = null)
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileDetailsPreview() {
    ProfileCardLayoutTheme() {
        UserProfileDetailsScreen(userID = 0,null)
    }
}
