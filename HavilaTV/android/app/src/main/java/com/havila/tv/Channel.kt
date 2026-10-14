package com.havila.tv

data class Channel(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val streamUrl: String? = null,
    val isLive: Boolean = false
)
