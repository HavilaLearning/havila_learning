package com.havila.tv

/** Official destinations and verified public direct media streams. */
object ChannelRepository {
    private fun localLogo(resourceName: String) =
        "android.resource://com.havila.tv/drawable/$resourceName"

    val channels = listOf(
        Channel("tvc", "TVC", "Notícias", "Notícias e Actualidade", null, false, null),
        Channel("stv", "STV", "Entretenimento", "Entretenimento", null, false, localLogo("stv_logo")),
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true, null),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", "https://stream.tvm.co.mz/hls/tvm/playlist.m3u8", true, localLogo("tvm_logo")),
        Channel("miramar", "Miramar", "Moçambique", "Informação, entretenimento e programação familiar", "https://miramar.co.mz/onde-assistir", true, localLogo("miramar_logo")),
        Channel("gospel-channel", "Gospel Channel", "Gospel", "Fé e Esperança", null, false, localLogo("gospel_channel_logo")),
        Channel("supersport", "SuperSport", "Desporto", "Desporto ao Vivo", null, false, localLogo("supersport_logo")),
        Channel("emmanuel-tv", "Emmanuel TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://emmanuel.tv/", true, localLogo("emanuel_tv_logo")),
        Channel("worldlove-tv", "WorldLoveTV", "Gospel", "Programação cristã internacional", null, false, localLogo("loveworld_sat_logo")),
        Channel("christ-tv", "Christ TV", "Gospel", "Canal cristão e programação de fé", null, false, localLogo("christ_tv_logo")),
        Channel("good-news-tv", "Good News TV", "Gospel", "Televisão cristã internacional", "https://www.mygoodnewstv.com/live", true, localLogo("goodnews_tv_logo")),
        Channel("hope-international-tv", "Hope International TV", "Gospel", "Fé, família, educação e esperança", null, false, localLogo("hope_channel_logo")),
        Channel("dw", "DW", "Notícias", "Informação internacional", "https://dwamdstream102.akamaized.net/hls/live/2015525/dwstream102/master.m3u8", true, localLogo("dw_logo")),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://cdnstack.internetmultimediaonline.org/lwsat/lwsat.stream/playlist.m3u8", true, localLogo("loveworld_sat_logo")),
        Channel("good-news-tv-kids", "Good News TV Kids", "Havila Kids", "Conteúdo cristão para crianças", "https://www.mygoodnewstv.com/live", true, localLogo("goodnews_tv_logo")),
        Channel("hope-channel-africa", "Hope Channel Africa", "Gospel", "Fé, família, saúde e educação", "https://hopeafrica.tv/ways-to-watch", true, localLogo("hope_channel_logo")),
        Channel("faith-tv", "Faith TV", "Gospel", "Programação cristã e familiar", "https://africa.myfaith.tv/", true, null),
        Channel("tbn", "TBN", "Gospel", "Conteúdo cristão, ensino e entretenimento", "https://www.tbn.org/watch", true, null),
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", "https://www.rtp.pt/play/direto/rtpafrica", true, null),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", "https://www.africanews.com/live/", true, null),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", "https://live.france24.com/hls/live/2037218/F24_EN_HI_HLS/master_5000.m3u8", true, null),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", "https://a-cdn.klowdtv.com/live3/euronews_720p/playlist.m3u8", true, null),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false, null),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false, null),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false, null),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false, null)
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
