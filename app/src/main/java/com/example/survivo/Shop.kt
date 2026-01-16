package com.example.survivo

data class Shop(
    val id: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val address: String = ""
)