package com.example.dogsapp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
class Dog(val name: String, val breed: String, private val _isPinned: Boolean,
        val dogPictureUrl: String) {

        var isPinned by mutableStateOf(_isPinned)
}