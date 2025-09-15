package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.InitialSettingsScreen // Updated import
// ViewModelのFactoryをimportに追加
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.InitialSettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // ViewModelのインスタンスをここで作成または取得
                val viewModel: InitialSettingsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        if (modelClass.isAssignableFrom(InitialSettingsViewModel::class.java)) {
                            @Suppress("UNCHECKED_CAST")
                            return InitialSettingsViewModel() as T
                        }
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                })
                MainApp(viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: InitialSettingsViewModel) { // ViewModelを引数に追加
    var currentScreen by remember { mutableStateOf(Screen.Main) }

    when (currentScreen) {
        Screen.Main -> MainScreen(onNavigateToSettings = { currentScreen = Screen.Settings })
        Screen.Settings -> InitialSettingsScreen(
            viewModel = viewModel, // ViewModelを渡す
            onSave = { currentScreen = Screen.Main })
    }
}

@Composable
fun MainScreen(onNavigateToSettings: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "メイン画面",
            )
            Button(onClick = onNavigateToSettings) {
                Text("初期設定を開く")
            }
        }
    }
}

enum class Screen {
    Main,
    Settings
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(onNavigateToSettings = {})
    }
}
