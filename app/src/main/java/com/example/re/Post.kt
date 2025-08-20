package com.example.re

import java.util.Date

data class Post(
    var id: Int = 0,
    var title: String = "",
    var content: String = "",
    var author: String = "",
    var createdAt: Date = Date(),
    var isLiked: Boolean = false,
)
