package com.example.re

data class Transaction(
    val method: String,
    val amount: Double,
    val details: String,
    val id: Long = System.currentTimeMillis()
)