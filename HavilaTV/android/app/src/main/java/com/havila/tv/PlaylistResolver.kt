package com.havila.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/**
 * Resolves a Havila TV channel from direct stream URLs or public M3U playlists.
 * The resolver accepts HTTP/HTTPS media endpoints even when the server does not
 * expose a .m3u8/.mpd extension in the URL.
 */
object PlaylistResolver {

    suspend fun resolve(channel: Channel): String? = withContext(Dispatchers.IO) {
        channel.streamUrl?.trim()?.takeIf(::isDirectMediaUrl)?.let { return@withContext it }

        val names = (listOf(channel.name) + channel.aliases)
            .map(::normalize)
            .filter { it.isNotBlank() }
            .distinct()

        for (playlistUrl in channel.playlistUrls) {
            val text = download(playlistUrl) ?: continue
            val resolved = parseM3u(text, names)
            if (resolved != null && isDirectMediaUrl(resolved)) {
                return@withContext resolved.trim()
            }
        }
        null
    }

    private fun download(url: String): String? {
        var connection: HttpURLConnection? = null
        return try {
            val parsed = URL(url)
            if (parsed.protocol !in setOf("http", "https")) return null
            connection = (parsed.openConnection() as HttpURLConnection).apply {
                connectTimeout = 12_000
                readTimeout = 20_000
                requestMethod = "GET"
                setRequestProperty("User-Agent", "HavilaTV/1.0 AndroidTV")
                setRequestProperty("Accept", "application/x-mpegURL, audio/mpegurl, application/vnd.apple.mpegurl, text/plain, */*")
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

            if (pendingName != null && matches(normalize(pendingName), names)) {
                return line
            }
            pendingName = null
        }
        return null
    }

    private fun extractDisplayName(extinf: String): String {
        val comma = extinf.indexOf(',')
        if (comma >= 0 && comma + 1 < extinf.length) return extinf.substring(comma + 1).trim()
        return Regex("tvg-name=\\\"([^\\\"]+)\\\"").find(extinf)?.groupValues?.getOrNull(1).orEmpty()
    }

    private fun matches(candidate: String, names: List<String>): Boolean {
        if (candidate.isBlank()) return false
        return names.any { expected ->
            candidate == expected || candidate.contains(expected) || expected.contains(candidate)
        }
    }

    private fun normalize(value: String): String = value
        .lowercase()
        .replace("&amp;", "and")
        .replace("tv network", "tv")
        .replace(Regex("[^a-z0-9]+"), " ")
        .trim()

    /**
     * A direct media URL is any valid HTTP/HTTPS endpoint that can be handed to
     * Media3. File extensions are useful hints but are not required by IPTV
     * servers, so extensionless endpoints are intentionally accepted.
     */
    fun isDirectMediaUrl(url: String): Boolean = try {
        val value = url.trim()
        val uri = URL(value)
        if (uri.protocol !in setOf("http", "https") || uri.host.isNullOrBlank()) return false

        val clean = value.substringBefore('?').lowercase()
        val path = uri.path.orEmpty().lowercase()
        val knownMediaExtension = path.endsWith(".m3u8") || path.endsWith(".mpd") ||
            path.endsWith(".mp4") || path.endsWith(".ts") || path.endsWith(".aac") ||
            path.endsWith(".mp3")

        knownMediaExtension ||
            path.contains("/live") || path.contains("/stream") || path.contains("/playlist") ||
            path.contains("/hls") || path.contains("/dash") ||
            clean.contains("m3u8") || clean.contains("manifest") || clean.contains("stream")
    } catch (_: Exception) {
        false
    }
}
