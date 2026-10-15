package com.havila.tv

/** Official destinations. A URL is treated as live only when an official online destination is known. */
object ChannelRepository {
    private fun logo(domain: String) = "https://www.google.com/s2/favicons?sz=128&domain=$domain"

    val channels = listOf(
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true, logo("mbc.co.mz")),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", "https://online.tvm.co.mz/", true, logo("tvm.co.mz")),
        Channel("tvm-internacional", "TVM Internacional", "Moçambique", "Programação moçambicana para África e o mundo", "https://online.tvm.co.mz/", true, logo("tvm.co.mz")),
        Channel("tv-sucesso", "TV Sucesso", "Moçambique", "Entretenimento e informação", "https://www.tvsucesso.co.mz/assistir-ao-vivo/", true, logo("tvsucesso.co.mz")),
        Channel("miramar", "Miramar", "Moçambique", "Informação, entretenimento e programação familiar", "https://miramar.co.mz/onde-assistir", true, logo("miramar.co.mz")),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false, logo("tvmana.co.mz")),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false, null),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false, null),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false, null),
        Channel("emmanuel-tv", "Emmanuel TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://emmanuel.tv/", true, logo("emmanuel.tv")),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://loveworldsat.org/live-tv/", true, logo("loveworldsat.org")),
        Channel("good-news-tv", "Good News TV", "Gospel", "Televisão cristã internacional", "https://www.mygoodnewstv.com/live", true, logo("mygoodnewstv.com")),
        Channel("good-news-tv-kids", "Good News TV Kids", "Havila Kids", "Conteúdo cristão para crianças", "https://www.mygoodnewstv.com/live", true, logo("mygoodnewstv.com")),
        Channel("tbn", "TBN", "Gospel", "Conteúdo cristão, ensino e entretenimento", "https://www.tbn.org/watch", true, logo("tbn.org")),
        Channel("hope-channel-africa", "Hope Channel Africa", "Gospel", "Fé, família, saúde e educação", "https://hopeafrica.tv/ways-to-watch", true, logo("hopeafrica.tv")),
        Channel("faith-tv", "Faith TV", "Gospel", "Programação cristã e familiar", "https://africa.myfaith.tv/", true, logo("myfaith.tv")),
        Channel("cmtv", "CMTV", "Gospel", "Conteúdo cristão africano e familiar", "https://www.cmtvfoundation.com/", true, logo("cmtvfoundation.com")),
        Channel("eternal-life-tv", "Eternal Life TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://eternallifetv.com/", true, logo("eternallifetv.com")),
        Channel("christ-tv", "Christ TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false, null),
        Channel("tz-gospel-tv", "TZ Gospel TV", "Gospel", "Canal Gospel — disponibilidade FTA por satélite", null, false, null),
        Channel("heaven-channel", "Heaven Channel", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false, null),
        Channel("king-james-tv", "King James TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false, null),
        Channel("glorious-times-tv", "Glorious Times TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false, null),
        Channel("hope-channel-malawi", "Hope Channel Malawi", "Gospel", "Canal cristão — disponibilidade por satélite", null, false, null),
        Channel("hope-channel-tanzania", "Hope Channel Tanzania", "Gospel", "Canal cristão — disponibilidade por satélite", null, false, null),
        Channel("startimes-gospel", "StarTimes Gospel", "Gospel", "Canal Gospel — disponibilidade por plataforma/satélite", null, false, null),
        Channel("kln-tv", "KLN TV", "Gospel", "Canal cristão — disponibilidade por plataforma/satélite", null, false, null),
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", "https://www.rtp.pt/play/direto/rtpafrica", true, logo("rtp.pt")),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", "https://www.africanews.com/live/", true, logo("africanews.com")),
        Channel("dw", "DW", "Notícias", "Informação internacional", "https://www.dw.com/en/live-tv/s-100825", true, logo("dw.com")),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", "https://www.france24.com/en/live", true, logo("france24.com")),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", "https://www.euronews.com/live", true, logo("euronews.com")),
        Channel("avo-tv", "AVO TV", "Entretenimento", "Canais FAST gratuitos em África", "https://www.avo.tv/", true, logo("avo.tv")),
        Channel("twale-tv", "Twale TV", "Entretenimento", "Filmes, música, desporto, família e fé", "https://www.twale.app/", true, logo("twale.app")),
        Channel("tugo-tv", "Tugo TV", "Família", "Família, notícias e conteúdos baseados na fé", "https://www.tugotv.com/", true, logo("tugotv.com"))
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
