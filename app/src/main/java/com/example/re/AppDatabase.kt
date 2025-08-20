package com.example.re

import android.content.Context

object AppDatabase {
    private var dao: LocationDao? = null

    fun getDatabase(context: Context): LocationDao {
        return dao ?: synchronized(this) {
            LocationDao(context.applicationContext).also { dao = it }
        }
    }
}