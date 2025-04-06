package com.example.dogsapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavigationExample()
                }
            }
        }
    }
}



@Composable
fun NavigationExample() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { Screen1(navController) }
        composable("screen2") { Screen2(navController) }
        composable("screen3") { Screen3(navController) }

    }
}

@Composable
fun Screen1(navController: NavController) {

    /////////////////// N A V B A R /////////////////

    Column (modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth().
                height(50.dp).background(Color.LightGray)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
        IconButton(
            onClick = { navController.navigate("screen2") }
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "settings",
            )
        }


        Text("DoggosApp",
            style = TextStyle(fontSize = 24.sp)
        )


        IconButton(
            onClick = { navController.navigate("screen3") }
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "settings",
            )
        }
    }}






    }


@Composable
fun Screen2(navController: NavController) {


    /////////////////// N A V B A R /////////////////

    Column (modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth().
                height(50.dp).background(Color.LightGray)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("screen1") }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                )
            }


            Text("Settings",
                style = TextStyle(fontSize = 24.sp)
            )


            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.Transparent
            )
        }}


}


@Composable
fun Screen3(navController: NavController) {

    /////////////////// N A V B A R /////////////////

    Column (modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth().
                height(50.dp).background(Color.LightGray)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("screen1") }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                )
            }


            Text("Profile",
                style = TextStyle(fontSize = 24.sp)
            )


            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.Transparent
            )
        }}
}



