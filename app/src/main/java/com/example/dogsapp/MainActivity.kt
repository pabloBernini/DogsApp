package com.example.dogsapp
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }
}

///////////////////////////     API     ////////////////////////////


data class DogApiResponse(
    val message: String,
    val status: String
)


interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogApiResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://dog.ceo/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}








val names = mutableStateListOf<Dog>()

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { Screen1(navController) }
        composable("screen2") { Screen2(navController) }
        composable("screen3") { Screen3(navController) }
        composable("screen4") { Screen4(navController) }
        composable(
            "screen5/{name}/{breed}/{dogPictureUrl}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("breed") { type = NavType.StringType },
                navArgument("dogPictureUrl") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val breed = backStackEntry.arguments?.getString("breed") ?: ""
            val dogPictureUrl = backStackEntry.arguments?.getString("dogPictureUrl") ?: ""
            Screen5(navController, name, breed, dogPictureUrl)
        }}
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

        var searchQuery by remember { mutableStateOf("") }
        val sortedNames = names
            .sortedWith(compareByDescending<Dog>{it.isPinned}
                .thenBy{it.name})
        val filteredList = remember(searchQuery, sortedNames, names) {
            val trimmedQuery = searchQuery.trim().lowercase()
            if (trimmedQuery.isBlank()) sortedNames
            else sortedNames.filter {
                it.name.lowercase().contains(trimmedQuery) ||
                it.breed.lowercase().contains(trimmedQuery)

            }

        }
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {searchQuery = it},
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
        Row(modifier = Modifier.padding(10.dp)) {
            Text("\uD83D\uDC36: ${names.size} ")
            Text("\uD83D\uDC97: ${names.count{it.isPinned}}")
        }

//////////////////////// L I S T /////////////////////////////
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            items(filteredList.size) { index ->

                // list item //
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
               /* Image(
                    painter = painterResource(id = R.drawable.doggo1),
                    contentDescription = "Doggo Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp).aspectRatio(1f)
*/
                Image(
                    painter = rememberAsyncImagePainter(filteredList[index].dogPictureUrl),
                    contentDescription = "Specific dog image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .aspectRatio(1f)
                        .padding(5.dp)
                )

                val encodedUrl = Uri.encode(filteredList[index].dogPictureUrl)
                Column(modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .clickable {
                        navController.navigate("screen5/" +
                                "${filteredList[index].name}/" +
                                "${filteredList[index].breed}/" +
                                encodedUrl
                        )
                    }){

                Text(text = filteredList[index].name, fontWeight = Bold)
                Text(text = filteredList[index].breed)
                }


                Row{

                IconButton(onClick = {
                    filteredList[index].isPinned = !filteredList[index].isPinned
                },modifier = Modifier.size(25.dp)) {
                        if(filteredList[index].isPinned){
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "pin"
                    )
                        } else
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "pined"
                            )
                }




                IconButton(onClick = {
                names.remove(filteredList[index])
                },modifier = Modifier.size(25.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete"
                    )
                }
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
        }

        Column(modifier = Modifier.padding(50.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally

            ){

        Image(
            painter = painterResource(id = R.drawable.profilowe),
            contentDescription = "Doggo Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).aspectRatio(1f))

        Text("Majk Wazowski")
        }
    }
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




        //////////////////  P H O T O ///////////////////////////


        var imageUrl by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        val coroutineScope = rememberCoroutineScope()
Column(modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally) {
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getRandomDogImage()
                }
                imageUrl = response.message
            } catch (e: Exception) {
                errorMessage = "Error loading image: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    if (imageUrl != null) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Random Dog Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(0.dp, 40.dp)
                .size(250.dp)
                .aspectRatio(1f)
        )
    } else if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.size(250.dp))
        Text("Loading Dog Image...")
    } else if (errorMessage != null) {
        Text(errorMessage!!)
    } else {
        Text("Failed to load dog image.")
    }


    /////////////// D O G    A D D I N G /////////

    var text by remember { mutableStateOf("") }
    var textSecond by remember { mutableStateOf("") }
    var nameExists by remember { mutableStateOf("") }



        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Dog's name") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )
    Text(nameExists, color = Color.Red, modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))
        OutlinedTextField(
            value = textSecond,
            onValueChange = { textSecond = it },
            placeholder = { Text("Dog's breed") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )



        Button(onClick = {

            if (names.any { it.name.equals(text, ignoreCase = true) }) {
                nameExists = "This name already exists"

            } else {
                nameExists = ""
                val newDog = Dog(text, textSecond, false, "$imageUrl")
                names.add(newDog)
                navController.navigate("screen1")
            }

        }, modifier = Modifier.padding(0.dp,40.dp)) {
            Text("Add")
        }
    }
}}

@Composable
fun Screen5(navController: NavController, name: String, breed: String, dogPictureUrl: String) {




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


            Text("Details",
                style = TextStyle(fontSize = 24.sp)
            )


            IconButton(
                onClick = {
                    names.removeIf{ it.name == name }
                    navController.navigate("screen1")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete",
                )
            }
        }

        Column(modifier = Modifier.padding(50.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(dogPictureUrl),
                contentDescription = "Random Dog Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .aspectRatio(1f)
                    .padding(10.dp)
            )
            Text(name, fontWeight = Bold)
             Text(breed)

        }

    }
}



