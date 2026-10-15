package com.havila.tv

/**
 * Havila TV channel catalogue.
 *
 * Direct .m3u8/.mpd URLs are preferred. Public IPTV-org regional/country
 * playlists are retained as discovery/fallback sources and are resolved by
 * the app only when a matching direct stream is found in the playlist.
 */
object ChannelRepository {
    private fun localLogo(resourceName: String) =
        "android.resource://com.havila.tv/drawable/$resourceName"

    // Public playlists supplied for this project.
    const val PLAYLIST_WAF = "https://iptv-org.github.io/iptv/regions/waf.m3u"
    const val PLAYLIST_WW = "https://iptv-org.github.io/iptv/regions/ww.m3u"
    const val PLAYLIST_SSA = "https://iptv-org.github.io/iptv/regions/ssa.m3u"
    const val PLAYLIST_EAF = "https://iptv-org.github.io/iptv/regions/eaf.m3u"
    const val PLAYLIST_AFR = "https://iptv-org.github.io/iptv/regions/afr.m3u"
    const val PLAYLIST_INT = "https://iptv-org.github.io/iptv/countries/int.m3u"
    const val PLAYLIST_ZW = "https://iptv-org.github.io/iptv/countries/zw.m3u"
    const val PLAYLIST_TZ = "https://iptv-org.github.io/iptv/countries/tz.m3u"
    const val PLAYLIST_ZA = "https://iptv-org.github.io/iptv/countries/za.m3u"
    const val PLAYLIST_PT = "https://iptv-org.github.io/iptv/countries/pt.m3u"
    const val PLAYLIST_NG = "https://iptv-org.github.io/iptv/countries/ng.m3u"
    const val PLAYLIST_MZ = "https://iptv-org.github.io/iptv/countries/mz.m3u"
    const val PLAYLIST_BR = "https://iptv-org.github.io/iptv/countries/br.m3u"
    const val PLAYLIST_UNDEFINED = "https://iptv-org.github.io/iptv/countries/undefined.m3u"

    val channels = listOf(
        Channel("tvc", "TVC", "Notícias", "Notícias e Actualidade", null, false, null,
            listOf(PLAYLIST_MZ), listOf("TVC", "TVC Moçambique")),
        Channel("stv", "STV", "Entretenimento", "Entretenimento", null, false, localLogo("stv_logo"),
            listOf(PLAYLIST_MZ), listOf("STV", "STV Moçambique")),
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true, null,
            listOf(PLAYLIST_MZ), listOf("MBC Moçambique", "MBC")),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", "https://stream.tvm.co.mz/hls/tvm/playlist.m3u8", true, localLogo("tvm_logo"),
            listOf(PLAYLIST_MZ), listOf("TVM", "TVM Moçambique")),
        Channel("miramar", "Miramar", "Moçambique", "Informação, entretenimento e programação familiar", null, false, localLogo("miramar_logo"),
            listOf(PLAYLIST_MZ), listOf("Miramar", "Miramar TV", "TV Miramar")),
        Channel("gospel-channel", "Gospel Channel", "Gospel", "Fé e Esperança", null, false, localLogo("gospel_channel_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("Gospel Channel")),
        Channel("supersport", "SuperSport", "Desporto", "Desporto ao Vivo", null, false, localLogo("supersport_logo"),
            listOf(PLAYLIST_ZA, PLAYLIST_SSA, PLAYLIST_AFR), listOf("SuperSport", "SuperSport Football")),
        Channel("emmanuel-tv", "Emmanuel TV", "Gospel", "Programação cristã e transmissão ao vivo", null, false, localLogo("emanuel_tv_logo"),
            listOf(PLAYLIST_NG, PLAYLIST_AFR, PLAYLIST_INT), listOf("Emmanuel TV", "EmmanuelTV")),
        Channel("worldlove-tv", "WorldLoveTV", "Gospel", "Programação cristã internacional", null, false, localLogo("loveworld_sat_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("WorldLoveTV", "World Love TV", "LoveWorld")),
        Channel("christ-tv", "Christ TV", "Gospel", "Canal cristão e programação de fé", null, false, localLogo("christ_tv_logo"),
            listOf(PLAYLIST_ZW, PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("Christ TV", "ChristTV")),
        Channel("good-news-tv", "Good News TV", "Gospel", "Televisão cristã internacional", null, false, localLogo("goodnews_tv_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("Good News TV", "GoodNews TV")),
        Channel("good-news-world", "Good News World TV", "Gospel", "Programação baseada na fé", null, false, localLogo("goodnews_tv_logo"),
            listOf(PLAYLIST_WW, PLAYLIST_AFR, PLAYLIST_INT), listOf("Good News World TV", "Good News World TV Networks", "Good News World")),
        Channel("hope-international-tv", "Hope International TV", "Gospel", "Fé, família, educação e esperança", null, false, localLogo("hope_channel_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("Hope International TV", "Hope Channel", "Hope Channel Africa")),
        Channel("dw", "DW", "Notícias", "Informação internacional", "https://dwamdstream102.akamaized.net/hls/live/2015525/dwstream102/master.m3u8", true, localLogo("dw_logo"),
            listOf(PLAYLIST_INT, PLAYLIST_AFR), listOf("DW", "Deutsche Welle")),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://cdnstack.internetmultimediaonline.org/lwsat/lwsat.stream/playlist.m3u8", true, localLogo("loveworld_sat_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("LoveworldSAT", "LoveWorld SAT")),
        Channel("hope-channel-africa", "Hope Channel Africa", "Gospel", "Fé, família, saúde e educação", null, false, localLogo("hope_channel_logo"),
            listOf(PLAYLIST_AFR, PLAYLIST_SSA), listOf("Hope Channel Africa", "Hope Channel")),
        Channel("faith-tv", "Faith TV", "Gospel", "Programação cristã e familiar", null, false, null,
            listOf(PLAYLIST_AFR, PLAYLIST_SSA), listOf("Faith TV")),
        Channel("tbn", "TBN", "Gospel", "Conteúdo cristão, ensino e entretenimento", null, false, null,
            listOf(PLAYLIST_WW, PLAYLIST_INT, PLAYLIST_AFR), listOf("TBN", "Trinity Broadcasting Network")),
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", null, false, null,
            listOf(PLAYLIST_PT, PLAYLIST_AFR, PLAYLIST_INT), listOf("RTP África", "RTP Africa")),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", null, false, null,
            listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("Africanews", "Africanews English")),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", "https://live.france24.com/hls/live/2037218/F24_EN_HI_HLS/master_5000.m3u8", true, null,
            listOf(PLAYLIST_WW, PLAYLIST_INT), listOf("France 24", "France24")),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", "https://a-cdn.klowdtv.com/live3/euronews_720p/playlist.m3u8", true, null,
            listOf(PLAYLIST_WW, PLAYLIST_INT), listOf("Euronews", "Euronews English")),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false, null,
            listOf(PLAYLIST_MZ, PLAYLIST_AFR), listOf("TV Mana", "TV Mana Moçambique")),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false, null,
            listOf(PLAYLIST_MZ, PLAYLIST_AFR), listOf("Reino Infantil")),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false, null,
            listOf(PLAYLIST_MZ), listOf("MNTV")),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false, null,
            listOf(PLAYLIST_MZ, PLAYLIST_AFR), listOf("TV Vitória", "TV Vitoria"))
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
