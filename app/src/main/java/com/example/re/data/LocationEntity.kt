package com.example.re.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_history")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val accuracy: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val provider: String
) 