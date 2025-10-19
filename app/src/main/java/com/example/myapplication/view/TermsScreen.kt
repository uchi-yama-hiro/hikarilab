package com.example.myapplication.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.viewmodel.TermsViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * 利用規約画面のUI
 * @param viewModel 利用規約画面のViewModel
 * @param onNavigateToNext 次の画面に遷移するためのコールバック
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(
    viewModel: TermsViewModel,
    onNavigateToNext: () -> Unit
) {
    // ViewModelから同意の状態を収集
    val agreed by viewModel.agreed.collectAsState()

    Scaffold(
        topBar = {
            // CenterAlignedTopAppBarなどの安定版コンポーネントに変更
            CenterAlignedTopAppBar(title = { Text("利用規約") })
        },
        bottomBar = {
            // 画面下部に固定するボタンエリア
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // 同意のチェックボックス
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = agreed,
                        onCheckedChange = { viewModel.toggleAgreed() }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("利用規約に同意する")
                }
                // 同意した場合のみボタンが有効になる
                Button(
                    onClick = onNavigateToNext,
                    enabled = agreed,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("同意して次へ")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 利用規約のテキスト表示エリア
            // 長い文章に対応するため、スクロール可能にする
            Text(
                text = stringResource(id = R.string.sample_terms), // あとで差し替えるサンプルテキスト
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f) // 残りのスペースをすべて使用する
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

// プレビュー用のComposable
@Preview(showBackground = true)
@Composable
fun TermsScreenPreview() {
    MyApplicationTheme {
        TermsScreen(viewModel = TermsViewModel(), onNavigateToNext = {})
    }
}
