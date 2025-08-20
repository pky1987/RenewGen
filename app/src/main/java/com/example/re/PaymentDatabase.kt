package com.example.re

import android.content.Context

object PaymentDatabase {
    private var dao: TransactionDao? = null

    fun getDatabase(context: Context): TransactionDao {
        return dao ?: synchronized(this) {
            TransactionDao(context.applicationContext).also { dao = it }
        }
    }
}