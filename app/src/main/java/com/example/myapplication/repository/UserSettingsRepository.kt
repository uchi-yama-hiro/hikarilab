package com.example.myapplication.repository

import com.example.myapplication.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    val userSettings: Flow<UserSettings>
    suspend fun saveUserSettings(userSettings: UserSettings)
}
