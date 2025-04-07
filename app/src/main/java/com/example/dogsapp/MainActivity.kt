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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

val dog1 = Dog("Boxy", "German Shepard")
val dog2 = Dog("Monty", "Retriever")
val dog3 = Dog("Taj", "Boxer")

val names = mutableStateListOf<Dog>(dog1, dog2, dog3)
@Composable
fun NavigationExample() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { Screen1(navController) }
        composable("screen2") { Screen2(navController) }
        composable("screen3") { Screen3(navController) }
        composable("screen4") { Screen4(navController) }

    }
}

@Composable
fun Screen1(navController: NavController) {

    Column (modifier = Modifier.fillMaxSize()){

    ///////////////////        N A V B A R        /////////////////

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
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
    }

 ///////////////////    S E A R C H    B A R    ////////////////

    var text by remember {mutableStateOf("")}

    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            placeholder = {Text("Search for doggos")},
            colors = TextFieldDefaults.colors(
                focusedContainerColor  = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.LightGray
            ),
            modifier = Modifier
                .weight(1f)
        )
        IconButton(onClick = {
            navController.navigate("screen4")
        },modifier = Modifier.size(50.dp)) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add"
            )
        }
    }

//////////////////////// L I S T /////////////////////////////

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            items(names.size) { dog ->

                // list item //
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                Text(text = names[dog].name)
                Text(text = names[dog].breed)

                IconButton(onClick = {
                    names.removeAt(dog)
                },modifier = Modifier.size(25.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "add"
                    )
                }
            }


            }
        }




    } ///end of column
} /// end screen1


@Composable
fun Screen2(navController: NavController) {


    /////////////////// N A V B A R /////////////////

    Column (modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
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
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
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


@Composable
fun Screen4(navController: NavController) {

    /////////////////// N A V B A R /////////////////

    Column (modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
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


            Text("Add Doggo",
                style = TextStyle(fontSize = 24.sp)
            )


            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.Transparent
            )
        }

    /////////////// D O G    A D D I N G /////////

        var text by remember {mutableStateOf("")}
        var textSecond by remember {mutableStateOf("")}

        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = text,
                onValueChange = {text = it},
                placeholder = {Text("Dog's name")},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor  = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
                modifier = Modifier
                    .weight(1f)
            )
            OutlinedTextField(
                value = textSecond,
                onValueChange = {textSecond = it},
                placeholder = {Text("Dog's name")},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor  = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.LightGray
                ),
                modifier = Modifier
                    .weight(1f)
            )



        Button(onClick = {
            val newDog = Dog(text, textSecond)
            names.add(newDog)
        }){
            Text("Przycisk")
        }
    }
}}




