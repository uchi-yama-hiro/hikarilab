package com.example.myapplication.model

data class UserSettings(
    var height: String = "",
    var weight: String = "",
    var age: String = "",
    var allergies: List<String> = emptyList(),
    var gender: Gender? = null
)
