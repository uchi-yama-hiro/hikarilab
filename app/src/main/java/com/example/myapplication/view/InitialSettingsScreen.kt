package com.example.myapplication.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.Gender
import com.example.myapplication.viewmodel.InitialSettingsViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

// Experimental APIを使用するためのオプトインアノテーション
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InitialSettingsScreen(
    // ViewModelのインスタンスを取得。デフォルトではHiltなどによる注入を期待するが、Previewでは直接生成も可能。
    viewModel: InitialSettingsViewModel = viewModel(),
    // 保存ボタンがクリックされたときに呼び出されるコールバック関数
    onSave: () -> Unit
) {
    // ViewModelからユーザー設定の状態を監視
    val userSettings = viewModel.userSettings

    // 各ドロップダウンメニューの選択肢リストを定義
    val heightOptions = (100..250).map { it.toString() } // 身長の選択肢 (100cmから250cm)
    val weightOptions = (30..200).map { it.toString() } // 体重の選択肢 (30kgから200kg)
    val ageOptions = (0..120).map { it.toString() }    // 年齢の選択肢 (0歳から120歳)
    // アレルギーの選択肢リスト
    val allergiesList = listOf("卵", "牛乳", "小麦", "えび", "かに", "そば", "落花生", "くるみ")

    // 各ドロップダウンメニューの展開状態を管理するローカル状態
    var heightExpanded by remember { mutableStateOf(false) } // 身長ドロップダウンの展開状態
    var weightExpanded by remember { mutableStateOf(false) } // 体重ドロップダウンの展開状態
    var ageExpanded by remember { mutableStateOf(false) }    // 年齢ドロップダウンの展開状態

    // Scaffoldを使用して基本的なマテリアルデザインのレイアウト構造を構築
    Scaffold(
        topBar = {
            // 上部のアプリバーを設定
            TopAppBar(title = { Text("初期設定") })
        },
        bottomBar = {
            // 下部のボタンバーを設定 (保存ボタン)
            Button(
                onClick = {
                    viewModel.saveSettings() // ViewModelに設定の保存を指示
                    onSave()                 // 保存後のナビゲーションなどを親コンポーネントに通知
                },
                modifier = Modifier
                    .fillMaxWidth() // 幅を最大に
                    .padding(16.dp) // 周囲にパディング
            ) {
                Text("保存")
            }
        }
    ) { innerPadding -> // Scaffold内のコンテンツエリアのパディング
        // メインコンテンツを表示する縦方向のColumn
        Column(
            modifier = Modifier
                .padding(innerPadding) // Scaffoldからの内部パディングを適用
                .padding(horizontal = 16.dp) // 全体の左右にパディングを追加
                .fillMaxSize() // 利用可能なスペース全体を占める
                .verticalScroll(rememberScrollState()), // コンテンツが画面に収まらない場合にスクロール可能にする
            horizontalAlignment = Alignment.Start, // 子要素を水平方向の開始位置に配置 (左揃え)
            verticalArrangement = Arrangement.spacedBy(8.dp) // 子要素間の垂直方向の間隔を設定
        ) {
            Spacer(Modifier.height(8.dp)) // 上部に少しスペースを追加

            // --- 身長設定セクション ---
            Text("身長 (cm)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.fillMaxWidth()) // ラベル
            Spacer(Modifier.height(4.dp)) // ラベルと入力フィールドの間のスペース
            ExposedDropdownMenuBox(
                expanded = heightExpanded, // ドロップダウンの展開状態
                onExpandedChange = { heightExpanded = !heightExpanded } // 展開状態の切り替え
            ) {
                TextField(
                    value = userSettings.height, // 表示する現在の身長
                    onValueChange = { viewModel.updateHeight(it) }, // 値の変更はViewModelに通知 (ここでは読み取り専用なので実際には呼ばれない想定)
                    modifier = Modifier
                        .fillMaxWidth() // 幅を最大に
                        .menuAnchor(), // ドロップダウンメニューのアンカーとして機能
                    readOnly = true, // ユーザーが直接テキスト入力できないようにする
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = heightExpanded) } // ドロップダウンの展開/折りたたみアイコン
                )
                ExposedDropdownMenu(
                    expanded = heightExpanded, // ドロップダウンの展開状態
                    onDismissRequest = { heightExpanded = false } // メニュー外をタップしたときに閉じる
                ) {
                    heightOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) }, // 各選択肢のテキスト
                            onClick = {
                                viewModel.updateHeight(selectionOption) // ViewModelに身長の更新を通知
                                heightExpanded = false // メニューを閉じる
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp)) // セクション間のスペース

            // --- 体重設定セクション ---
            Text("体重 (kg)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.fillMaxWidth()) // ラベル
            Spacer(Modifier.height(4.dp)) // ラベルと入力フィールドの間のスペース
            ExposedDropdownMenuBox(
                expanded = weightExpanded,
                onExpandedChange = { weightExpanded = !weightExpanded }
            ) {
                TextField(
                    value = userSettings.weight,
                    onValueChange = { viewModel.updateWeight(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = weightExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = weightExpanded,
                    onDismissRequest = { weightExpanded = false }
                ) {
                    weightOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateWeight(selectionOption) // ViewModelに体重の更新を通知
                                weightExpanded = false // メニューを閉じる
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp)) // セクション間のスペース

            // --- 年齢設定セクション ---
            Text("年齢", style = MaterialTheme.typography.titleSmall, modifier = Modifier.fillMaxWidth()) // ラベル
            Spacer(Modifier.height(4.dp)) // ラベルと入力フィールドの間のスペース
            ExposedDropdownMenuBox(
                expanded = ageExpanded,
                onExpandedChange = { ageExpanded = !ageExpanded }
            ) {
                TextField(
                    value = userSettings.age,
                    onValueChange = { viewModel.updateAge(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ageExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = ageExpanded,
                    onDismissRequest = { ageExpanded = false }
                ) {
                    ageOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateAge(selectionOption) // ViewModelに年齢の更新を通知
                                ageExpanded = false // メニューを閉じる
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp)) // セクション間のスペース

            // --- アレルギー設定セクション ---
            Text("アレルギー (複数選択可)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth()) // ラベル
            Spacer(Modifier.height(8.dp)) // ラベルとチップ群の間のスペース
            // FlowRowを使用して、コンテンツが幅を超えた場合に自動的に折り返すチップのレイアウトを実装
            FlowRow(
                modifier = Modifier.fillMaxWidth(), // 幅を最大に
                horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平方向のチップ間のスペース
                verticalArrangement = Arrangement.spacedBy(8.dp) // 垂直方向のチップ間のスペース (折り返し時)
            ) {
                allergiesList.forEach { allergy ->
                    FilterChip(
                        selected = viewModel.selectedAllergies.contains(allergy), // ViewModelの選択状態に基づいてチップの選択状態を決定
                        onClick = { viewModel.toggleAllergy(allergy) }, // チップクリック時にViewModelのアレルギー選択状態をトグル
                        label = { Text(allergy) } // チップに表示するアレルギー名
                    )
                }
            }
            Spacer(Modifier.height(16.dp)) // セクション間のスペース

            // --- 性別設定セクション ---
            Text("性別", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth()) // ラベル
            Spacer(Modifier.height(8.dp)) // ラベルとセグメントボタンの間のスペース
            // SingleChoiceSegmentedButtonRowを使用して、単一選択のセグメントボタンを実装
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                Gender.entries.forEachIndexed { index, gender ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = Gender.entries.size), // ボタンの形状を定義
                        onClick = { viewModel.updateGender(gender) }, // ボタンクリック時にViewModelに性別の更新を通知
                        selected = gender == userSettings.gender // ViewModelの現在の性別に基づいてボタンの選択状態を決定
                    ) {
                        Text(gender.displayName) // ボタンに表示する性別の表示名
                    }
                }
            }
            Spacer(Modifier.height(16.dp)) // 下部に少しスペースを追加
        }
    }
}

// Android Studioのプレビュー機能のためのComposable関数
@Preview(showBackground = true)
@Composable
fun InitialSettingsScreenPreview() {
    MyApplicationTheme {
        // プレビュー用にViewModelのダミーインスタンスと空のonSaveコールバックを渡す
        InitialSettingsScreen(viewModel = InitialSettingsViewModel(), onSave = {})
    }
}
