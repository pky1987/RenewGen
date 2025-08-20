package com.example.re

data class GeocodedLocation(
    val latitude: Double,
    val longitude: Double,
    val locality: String,
    val area: String,
    val city: String,
    val district: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val timestamp: Long
)
