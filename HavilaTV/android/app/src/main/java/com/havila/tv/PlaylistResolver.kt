package com.havila.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/**
 * Resolves a Havila TV channel from public M3U playlists into a direct media URL.
 *
 * The resolver never opens a website. It only accepts media URLs such as .m3u8/.mpd
 * and matches EXTINF entries using the channel name and aliases.
 */
object PlaylistResolver {

    suspend fun resolve(channel: Channel): String? = withContext(Dispatchers.IO) {
        channel.streamUrl?.takeIf(::isDirectMediaUrl)?.let { return@withContext it }

        val names = (listOf(channel.name) + channel.aliases)
            .map(::normalize)
            .filter { it.isNotBlank() }
            .distinct()

        for (playlistUrl in channel.playlistUrls) {
            val text = download(playlistUrl) ?: continue
            val resolved = parseM3u(text, names)
            if (resolved != null && isDirectMediaUrl(resolved)) {
                return@withContext resolved
            }
        }
        null
    }

    private fun download(url: String): String? {
        var connection: HttpURLConnection? = null
        return try {
            connection = (URL(url).openConnection() as HttpURLConnection).apply {
                connectTimeout = 12_000
                readTimeout = 20_000
                requestMethod = "GET"
                setRequestProperty("User-Agent", "HavilaTV/1.0 AndroidTV")
                setRequestProperty("Accept", "application/x-mpegURL, audio/mpegurl, text/plain, */*")
                instanceFollowRedirects = true
            }
            if (connection.responseCode !in 200..299) return null
            connection.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        } catch (_: Exception) {
            null
        } finally {
            connection?.disconnect()
        }
    }

    private fun parseM3u(text: String, names: List<String>): String? {
        val lines = text.lineSequence().map { it.trim() }.filter { it.isNotEmpty() }.toList()
        var pendingName: String? = null

        for (line in lines) {
            if (line.startsWith("#EXTINF", ignoreCase = true)) {
                pendingName = extractDisplayName(line)
                continue
            }

            if (line.startsWith("#")) continue

            val mediaUrl = line
            val candidateName = normalize(pendingName.orEmpty())
            if (pendingName != null && matches(candidateName, names)) {
                return mediaUrl
            }
            pendingName = null
        }
        return null
    }

    private fun extractDisplayName(extinf: String): String {
        val comma = extinf.indexOf(',')
        if (comma >= 0 && comma + 1 < extinf.length) {
            return extinf.substring(comma + 1).trim()
        }
        val tvgName = Regex("tvg-name=\\\"([^\\\"]+)\\\"").find(extinf)?.groupValues?.getOrNull(1)
        return tvgName.orEmpty()
    }

    private fun matches(candidate: String, names: List<String>): Boolean {
        if (candidate.isBlank()) return false
        return names.any { expected ->
            candidate == expected ||
                candidate.contains(expected) ||
                expected.contains(candidate)
        }
    }

    private fun normalize(value: String): String = value
        .lowercase()
        .replace("&amp;", "and")
        .replace("tv network", "tv")
        .replace(Regex("[^a-z0-9]+"), " ")
        .trim()

    fun isDirectMediaUrl(url: String): Boolean {
        val clean = url.substringBefore('?').lowercase()
        return clean.endsWith(".m3u8") ||
            clean.endsWith(".mpd") ||
            clean.endsWith(".mp4") ||
            clean.endsWith(".ts")
    }
}
