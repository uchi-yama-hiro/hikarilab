package com.example.myapplication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.Budget
import com.example.myapplication.model.Genre
import com.example.myapplication.model.HotPepperApi
import com.example.myapplication.model.PcPhoto
import com.example.myapplication.model.Photo
import com.example.myapplication.model.Shop
import com.example.myapplication.model.Urls
import com.example.myapplication.viewmodel.StoreSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreSearchScreen(onBackClicked: () -> Unit, viewModel: StoreSearchViewModel = viewModel()) {
    var keyword by remember { mutableStateOf("") }
    val shops by viewModel.shops.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("店舗検索") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = keyword,
                    onValueChange = { keyword = it },
                    label = { Text("キーワード") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.searchRestaurants(HotPepperApi.API_KEY, keyword) }) {
                    Text("検索")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(shops) { shop ->
                        ShopItem(shop)
                    }
                }
            }
        }
    }
}

@Composable
fun ShopItem(shop: Shop) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = shop.name, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Text(text = shop.address)
            Text(text = shop.genre.name)
            Text(text = shop.access)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemPreview() {
    val sampleShop = Shop(
        id = "J001278855",
        name = "焼肉 ホルモンとくちゃん 平間店",
        logoImage = "https://imgfp.hotp.jp/IMGH/05/28/P038230528/P038230528_69.jpg",
        nameKana = "ヤキニク ホルモン トクチャン ヒラマテン",
        address = "神奈川県川崎市中原区田尻町２０",
        stationName = "平間",
        lat = 35.5562939498,
        lng = 139.6759714995,
        genre = Genre(name = "焼肉・ホルモン", code = "G008"),
        budget = Budget(name = "3001～4000円", code = "B003", average = "3500円"),
        `catch` = "平間で焼肉居酒屋なら\n和牛や新鮮ホルモンを！",
        access = "ＪＲ南武線平間駅出口より徒歩約2分",
        urls = Urls(pc = "https://www.hotpepper.jp/strJ001278855/?vos=nhppalsa000016"),
        photo = Photo(
            pc = PcPhoto(
                l = "https://imgfp.hotp.jp/IMGH/05/28/P038230528/P038230528_238.jpg",
                m = "https://imgfp.hotp.jp/IMGH/05/28/P038230528/P038230528_168.jpg",
                s = "https://imgfp.hotp.jp/IMGH/05/28/P038230528/P038230528_58_s.jpg"
            )
        )
    )
    ShopItem(shop = sampleShop)
}
