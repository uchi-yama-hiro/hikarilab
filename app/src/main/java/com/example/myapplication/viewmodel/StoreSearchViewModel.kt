package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.HotPepperApiService
import com.example.myapplication.model.Shop
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class StoreSearchViewModel : ViewModel() {

    private val _shops = MutableStateFlow<List<Shop>>(emptyList())
    val shops: StateFlow<List<Shop>> = _shops.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://webservice.recruit.co.jp/hotpepper/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val apiService = retrofit.create(HotPepperApiService::class.java)

    fun searchRestaurants(apiKey: String, keyword: String) {
        viewModelScope.launch {
            _isSearching.value = true
            try {
                val response = apiService.searchRestaurants(apiKey, keyword)
                _shops.value = response.results.shop
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isSearching.value = false
            }
        }
    }
}
