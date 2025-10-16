package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.NutritionStatusViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionStatusScreen(viewModel: NutritionStatusViewModel = viewModel()) {
    val nutrients by viewModel.nutrients.collectAsState()
    var isFabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isFabExpanded) {
                    FloatingActionButton(onClick = { /* TODO: 食事記録画面へのナビゲーション */ }) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Restaurant, contentDescription = "食事記録")
                            Spacer(Modifier.width(8.dp))
                            Text("食事記録")
                        }
                    }
                    FloatingActionButton(onClick = { /* TODO: 個人設定画面へのナビゲーション */ }) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Settings, contentDescription = "個人設定")
                            Spacer(Modifier.width(8.dp))
                            Text("個人設定")
                        }
                    }
                }
                FloatingActionButton(onClick = { isFabExpanded = !isFabExpanded }) {
                    Icon(
                        imageVector = if (isFabExpanded) Icons.Filled.Close else Icons.Filled.Add,
                        contentDescription = if (isFabExpanded) "閉じる" else "開く"
                    )
                }
            }
        }
    ) { innerPadding ->
        val calorieIntake = nutrients.find { it.name == "カロリー" }
        val otherNutrients = nutrients.filter { it.name != "カロリー" }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateNavigator()
            Spacer(Modifier.height(8.dp))

            Text(
                "1日の摂取状況",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))

            calorieIntake?.let {
                CalorieCircularChart(nutrient = it)
                Spacer(Modifier.height(24.dp))
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                otherNutrients.forEach { nutrient ->
                    NutrientBar(nutrient)
                    Spacer(Modifier.height(12.dp))
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { /* TODO */ }, modifier = Modifier.weight(1f)) {
                    Text("推奨カロリー")
                }
            }
            Spacer(Modifier.weight(1f)) // Push buttons to the bottom
        }
    }
}

@Composable
fun DateNavigator() {
    // Note for user: Date should be managed by a ViewModel
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M月d日 (E)", Locale.JAPAN)

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Previous day */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "前の日")
            }
            Text(
                text = currentDate.format(formatter),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { /* TODO: Next day */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "次の日")
            }
        }
        TextButton(
            onClick = { /* TODO: Navigate to week view */ },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text("一週間")
        }
    }
}


@Composable
fun CalorieCircularChart(nutrient: NutritionStatusViewModel.NutrientIntake) {
    val fulfillmentRate = if (nutrient.goal > 0) nutrient.total / nutrient.goal else 0f
    val fulfillmentPercentage = fulfillmentRate * 100
    val progressColor = when {
        fulfillmentRate > 1.2f -> MaterialTheme.colorScheme.error // Red for excessive over
        fulfillmentRate > 1.0f -> Color(0xFFFFC107) // Yellow for over
        else -> Color(0xFF4CAF50) // Green for not over
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(180.dp)) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 16.dp,
            strokeCap = StrokeCap.Round
        )
        CircularProgressIndicator(
            progress = { fulfillmentRate.coerceAtMost(1f) },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 16.dp,
            strokeCap = StrokeCap.Round,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format(Locale.US, "%.0f", nutrient.total),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = "kcal", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "充足率 ${String.format(Locale.US, "%.0f", fulfillmentPercentage)}%",
                fontSize = 14.sp,
                color = progressColor
            )
        }
    }
}


@Composable
fun NutrientBar(nutrient: NutritionStatusViewModel.NutrientIntake) {
    val unit = if (nutrient.name == "カロリー") "kcal" else "g"
    val fulfillmentRate = if (nutrient.goal > 0) nutrient.total / nutrient.goal else 0f
    val progressColor = when {
        fulfillmentRate > 1.2f -> MaterialTheme.colorScheme.error // Red for excessive over
        fulfillmentRate > 1.0f -> Color(0xFFFFC107) // Yellow for over
        else -> Color(0xFF4CAF50) // Green for not over
    }
    val textColor = if (fulfillmentRate > 1.0f) progressColor else Color.Unspecified


    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(nutrient.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(
                "${String.format(Locale.US, "%.0f", nutrient.total)} / ${String.format(Locale.US, "%.0f", nutrient.goal)} $unit",
                fontSize = 14.sp,
                color = textColor
            )
        }
        Spacer(Modifier.height(4.dp))
        // 充足率グラフ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = fulfillmentRate.coerceAtMost(1.0f))
                    .background(progressColor)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                "充足率 ${String.format(Locale.US, "%.0f", fulfillmentRate * 100)}%",
                fontSize = 12.sp,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionStatusScreenPreview() {
    MaterialTheme {
        NutritionStatusScreen()
    }
}
