package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Gender
import com.example.myapplication.model.UserSettings
import com.example.myapplication.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

/**
 * 栄養摂取状況画面のViewModel
 */
class NutritionStatusViewModel : ViewModel() {

    // 栄養素のデータ構造
    data class NutrientIntake(
        val name: String,
        val morning: Float,
        val lunch: Float,
        val evening: Float,
        val goal: Float
    ) {
        val total: Float
            get() = morning + lunch + evening
    }

    // 5大栄養素のサンプルデータ
    private val _nutrients = MutableStateFlow(
        listOf(
            NutrientIntake(name = "カロリー", morning = 500f, lunch = 700f, evening = 600f, goal = 2200f),
            NutrientIntake(name = "タンパク質", morning = 20f, lunch = 30f, evening = 25f, goal = 100f),
            NutrientIntake(name = "脂質", morning = 15f, lunch = 20f, evening = 18f, goal = 70f),
            NutrientIntake(name = "炭水化物", morning = 50f, lunch = 70f, evening = 60f, goal = 300f),
            NutrientIntake(name = "ビタミン", morning = 10f, lunch = 15f, evening = 12f, goal = 50f),
            NutrientIntake(name = "ミネラル", morning = 8f, lunch = 10f, evening = 9f, goal = 40f)
        )
    )
    val nutrients = _nutrients.asStateFlow()


}

/**
 * プレビューやテスト用のダミーRepository
 */
private class DummyUserSettingsRepository : UserSettingsRepository {
    override val userSettings: Flow<UserSettings> = flowOf(
        UserSettings(
            height = "170",
            weight = "65",
            age = "30",
            gender = Gender.MALE,
            allergies = emptyList()
        )
    )

    override suspend fun saveUserSettings(userSettings: UserSettings) {
        // 何もしない
    }
}