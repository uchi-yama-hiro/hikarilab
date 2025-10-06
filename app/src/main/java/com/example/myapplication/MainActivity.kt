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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.InitialSettingsScreen
import com.example.myapplication.view.TermsScreen
import com.example.myapplication.viewmodel.InitialSettingsViewModel
import com.example.myapplication.viewmodel.TermsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

/**
 * 画面遷移を管理するComposable
 */
@Composable
fun MainApp() {
    // 現在表示している画面の状態を管理
    var currentScreen by remember { mutableStateOf(Screen.Main) }

    // currentScreenの状態に応じて表示する画面を切り替える
    when (currentScreen) {
        // メイン画面
        Screen.Main -> MainScreen(
            onNavigateToNext = { currentScreen = Screen.Terms } // ボタンクリックで規約画面へ
        )
        // 利用規約画面
        Screen.Terms -> {
            val termsViewModel: TermsViewModel = viewModel()
            TermsScreen(
                viewModel = termsViewModel,
                onNavigateToNext = { currentScreen = Screen.Settings } // 同意して次へで設定画面へ
            )
        }
        // 初期設定画面
        Screen.Settings -> {
            // InitialSettingsViewModelをここで取得
            val settingsViewModel: InitialSettingsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(InitialSettingsViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return InitialSettingsViewModel() as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            })
            InitialSettingsScreen(
                viewModel = settingsViewModel,
                onSave = { currentScreen = Screen.Main } // 保存したらメイン画面へ
            )
        }
    }
}

/**
 * アプリの最初の画面
 */
@Composable
fun MainScreen(onNavigateToNext: () -> Unit, modifier: Modifier = Modifier) {
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
            Button(onClick = onNavigateToNext) {
                Text("初期設定を開始する")
            }
        }
    }
}

/**
 * 画面の種類を定義するenum
 */
enum class Screen {
    Main, // メイン画面
    Terms, // 利用規約画面
    Settings // 初期設定画面
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(onNavigateToNext = {})
    }
}
