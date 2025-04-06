package com.example.dogsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.dogsapp.ui.theme.DogsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            DogsAppTheme {
                    DogsList()
                }
            }
        }
    }


@Composable
fun DogsList() {
    val dogNames = arrayOf("Azor", "Burek", "Reksio", "Lesi", "Szarik")

    LazyColumn {
        item {
            Text(text = "Lista piesków")
        }
        items(dogNames.size) { index ->
            Text(text = dogNames[index])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DogsAppTheme {
        DogsList()
    }
}