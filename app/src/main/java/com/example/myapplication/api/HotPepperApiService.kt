package com.example.myapplication.api

import com.example.myapplication.model.GourmetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HotPepperApiService {
    @GET("gourmet/v1/")
    suspend fun searchRestaurants(
        @Query("key") apiKey: String,
        @Query("keyword") keyword: String,
        @Query("format") format: String = "json"
    ): GourmetResponse
}
