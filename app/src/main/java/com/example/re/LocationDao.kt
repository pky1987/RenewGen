package com.example.re

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationDao(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("locations", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "location_data"
    private val maxItems = 100

    fun insert(location: LocationEntity) {
        val currentList = getAll().toMutableList().apply {
            if (size >= maxItems) removeAt(size - 1)
            add(0, location)
        }
        sharedPrefs.edit().putString(key, gson.toJson(currentList)).apply()
    }

    fun getRecent(offset: Int, limit: Int): List<LocationEntity> {
        return getAll().let {
            it.subList(offset.coerceAtMost(it.size), (offset + limit).coerceAtMost(it.size))
        }
    }
    private fun getAll() = sharedPrefs.getString(key, null)?.let {
        gson.fromJson<List<LocationEntity>>(it, object : TypeToken<List<LocationEntity>>() {}.type)
    } ?: emptyList()
}