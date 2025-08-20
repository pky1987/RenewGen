package com.example.re

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Energy(
    val name: String,
    val price: String,
    val imageRes: Int
) : Parcelable