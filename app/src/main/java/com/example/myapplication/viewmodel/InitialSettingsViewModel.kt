package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Gender
import com.example.myapplication.model.UserSettings

class InitialSettingsViewModel : ViewModel() {
    var userSettings by mutableStateOf(UserSettings())
        private set

    val selectedAllergies = mutableStateListOf<String>()

    fun updateHeight(height: String) {
        userSettings = userSettings.copy(height = height)
    }

    fun updateWeight(weight: String) {
        userSettings = userSettings.copy(weight = weight)
    }

    fun updateAge(age: String) {
        userSettings = userSettings.copy(age = age)
    }

    fun toggleAllergy(allergy: String) {
        if (selectedAllergies.contains(allergy)) {
            selectedAllergies.remove(allergy)
        } else {
            selectedAllergies.add(allergy)
        }
        userSettings = userSettings.copy(allergies = selectedAllergies.toList())
    }

    fun updateGender(gender: Gender) {
        userSettings = userSettings.copy(gender = gender)
    }

    fun saveSettings() {
        // ここで永続化処理を行う (例: SharedPreferences, Room, DataStore)
        println("Settings saved: $userSettings")
    }
}
