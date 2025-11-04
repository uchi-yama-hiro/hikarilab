package com.example.myapplication.model

import com.squareup.moshi.Json

data class GourmetResponse(
    val results: Results
)

data class Results(
    @Json(name = "api_version")
    val apiVersion: String,
    @Json(name = "results_available")
    val resultsAvailable: Int,
    @Json(name = "results_returned")
    val resultsReturned: String,
    @Json(name = "results_start")
    val resultsStart: Int,
    val shop: List<Shop>
)

data class Shop(
    val id: String,
    val name: String,
    @Json(name = "logo_image")
    val logoImage: String,
    @Json(name = "name_kana")
    val nameKana: String,
    val address: String,
    @Json(name = "station_name")
    val stationName: String,
    val lat: Double,
    val lng: Double,
    val genre: Genre,
    val budget: Budget,
    val `catch`: String,
    val access: String,
    val urls: Urls,
    val photo: Photo
)

data class Genre(
    val name: String,
    val code: String
)

data class Budget(
    val name: String,
    val code: String,
    val average: String
)

data class Urls(
    val pc: String
)

data class Photo(
    val pc: PcPhoto
)

data class PcPhoto(
    val l: String,
    val m: String,
    val s: String
)
