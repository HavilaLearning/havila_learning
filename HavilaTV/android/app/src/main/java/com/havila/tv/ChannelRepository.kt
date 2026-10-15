package com.havila.tv

/** Official destinations and verified public direct media streams. */
object ChannelRepository {
    private fun logo(domain: String) = "https://www.google.com/s2/favicons?sz=128&domain=$domain"

    val channels = listOf(
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true, logo("mbc.co.mz")),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", "https://stream.tvm.co.mz/hls/tvm/playlist.m3u8", true, logo("tvm.co.mz")),
        Channel("miramar", "Miramar", "Moçambique", "Informação, entretenimento e programação familiar", "https://miramar.co.mz/onde-assistir", true, logo("miramar.co.mz")),
        Channel("emmanuel-tv", "Emmanuel TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://emmanuel.tv/", true, logo("emmanuel.tv")),
        Channel("worldlove-tv", "WorldLoveTV", "Gospel", "Programação cristã internacional", null, false, logo("loveworldsat.org")),
        Channel("christ-tv", "Christ TV", "Gospel", "Canal cristão e programação de fé", null, false, logo("christianworldmedia.com")),
        Channel("good-news-tv", "Good News TV", "Gospel", "Televisão cristã internacional", "https://www.mygoodnewstv.com/live", true, logo("mygoodnewstv.com")),
        Channel("hope-international-tv", "Hope International TV", "Gospel", "Fé, família, educação e esperança", null, false, logo("hopechannelinternational.org")),
        Channel("dw", "DW", "Notícias", "Informação internacional", "https://dwamdstream102.akamaized.net/hls/live/2015525/dwstream102/master.m3u8", true, logo("dw.com")),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://cdnstack.internetmultimediaonline.org/lwsat/lwsat.stream/playlist.m3u8", true, logo("loveworldsat.org")),
        Channel("good-news-tv-kids", "Good News TV Kids", "Havila Kids", "Conteúdo cristão para crianças", "https://www.mygoodnewstv.com/live", true, logo("mygoodnewstv.com")),
        Channel("hope-channel-africa", "Hope Channel Africa", "Gospel", "Fé, família, saúde e educação", "https://hopeafrica.tv/ways-to-watch", true, logo("hopeafrica.tv")),
        Channel("faith-tv", "Faith TV", "Gospel", "Programação cristã e familiar", "https://africa.myfaith.tv/", true, logo("myfaith.tv")),
        Channel("tbn", "TBN", "Gospel", "Conteúdo cristão, ensino e entretenimento", "https://www.tbn.org/watch", true, logo("tbn.org")),
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", "https://www.rtp.pt/play/direto/rtpafrica", true, logo("rtp.pt")),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", "https://www.africanews.com/live/", true, logo("africanews.com")),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", "https://live.france24.com/hls/live/2037218/F24_EN_HI_HLS/master_5000.m3u8", true, logo("france24.com")),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", "https://a-cdn.klowdtv.com/live3/euronews_720p/playlist.m3u8", true, logo("euronews.com")),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false, logo("tvmana.co.mz")),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false, null),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false, null),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false, null)
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
