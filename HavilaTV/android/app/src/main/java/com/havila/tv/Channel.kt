package com.havila.tv

data class Channel(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val streamUrl: String? = null,
    val isLive: Boolean = false,
    val logoUrl: String? = null,
    /** Public M3U playlist sources used as a fallback when no direct stream is known. */
    val playlistUrls: List<String> = emptyList(),
    /** Alternative names used when matching EXTINF entries in playlists. */
    val aliases: List<String> = emptyList(),
    /** Havila TV subcategory used by the category/subcategory/channel navigation. */
    val subcategory: String = ""
)
