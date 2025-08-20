package com.example.re

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TransactionDao(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("transactions_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun insert(transaction: Transaction) {
        val transactions = getAllTransactions().toMutableList().apply { add(transaction) }
        sharedPreferences.edit().putString("transactions", gson.toJson(transactions)).apply()
    }

    fun getAllTransactions(): List<Transaction> {
        return sharedPreferences.getString("transactions", null)?.let { json ->
            gson.fromJson(json, object : TypeToken<List<Transaction>>() {}.type)
        } ?: emptyList()
    }
}