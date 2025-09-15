package com.example.myapplication.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialSettingsScreen(
    viewModel: InitialSettingsViewModel = viewModel(),
    onSave: () -> Unit
) {
    val userSettings = viewModel.userSettings

    // Dropdown menu options
    val heightOptions = (100..250).map { it.toString() }
    val weightOptions = (30..200).map { it.toString() }
    val ageOptions = (0..120).map { it.toString() }
    val allergiesList = listOf("卵", "牛乳", "小麦", "えび", "かに", "そば", "落花生")

    var heightExpanded by remember { mutableStateOf(false) }
    var weightExpanded by remember { mutableStateOf(false) }
    var ageExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("初期設定") })
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.saveSettings()
                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("保存")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 身長
            ExposedDropdownMenuBox(
                expanded = heightExpanded,
                onExpandedChange = { heightExpanded = !heightExpanded }
            ) {
                TextField(
                    value = userSettings.height,
                    onValueChange = { viewModel.updateHeight(it) }, // ViewModel経由で更新
                    label = { Text("身長 (cm)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = heightExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = heightExpanded,
                    onDismissRequest = { heightExpanded = false }
                ) {
                    heightOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateHeight(selectionOption)
                                heightExpanded = false
                            }
                        )
                    }
                }
            }

            // 体重
            ExposedDropdownMenuBox(
                expanded = weightExpanded,
                onExpandedChange = { weightExpanded = !weightExpanded }
            ) {
                TextField(
                    value = userSettings.weight,
                    onValueChange = { viewModel.updateWeight(it) }, // ViewModel経由で更新
                    label = { Text("体重 (kg)") },
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
                                viewModel.updateWeight(selectionOption)
                                weightExpanded = false
                            }
                        )
                    }
                }
            }

            // 年齢
            ExposedDropdownMenuBox(
                expanded = ageExpanded,
                onExpandedChange = { ageExpanded = !ageExpanded }
            ) {
                TextField(
                    value = userSettings.age,
                    onValueChange = { viewModel.updateAge(it) }, // ViewModel経由で更新
                    label = { Text("年齢") },
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
                                viewModel.updateAge(selectionOption)
                                ageExpanded = false
                            }
                        )
                    }
                }
            }

            // アレルギー
            Text("アレルギー (複数選択可)", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                items(allergiesList) { allergy ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = viewModel.selectedAllergies.contains(allergy),
                            onCheckedChange = { viewModel.toggleAllergy(allergy) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(allergy)
                    }
                }
            }

            // 性別
            Text("性別", style = MaterialTheme.typography.titleMedium)
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                Gender.entries.forEachIndexed { index, gender ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = Gender.entries.size),
                        onClick = { viewModel.updateGender(gender) },
                        selected = gender == userSettings.gender
                    ) {
                        Text(gender.displayName)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialSettingsScreenPreview() {
    MyApplicationTheme {
        // PreviewではViewModelを直接インスタンス化するか、モックを使用します
        InitialSettingsScreen(viewModel = InitialSettingsViewModel(), onSave = {})
    }
}
