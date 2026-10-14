package com.havila.tv

/** Public/official destinations. Streams are only marked live when an official online destination is known. */
object ChannelRepository {
    val channels = listOf(
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", "https://online.tvm.co.mz/", true),
        Channel("tvm-internacional", "TVM Internacional", "Moçambique", "Programação moçambicana para África e o mundo", "https://online.tvm.co.mz/", true),
        Channel("tv-sucesso", "TV Sucesso", "Moçambique", "Entretenimento e informação", "https://www.tvsucesso.co.mz/assistir-ao-vivo/", true),
        Channel("miramar", "Miramar", "Moçambique", "Informação, entretenimento e programação familiar", "https://miramar.co.mz/onde-assistir", true),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false),

        // Gospel / Christian: official live destinations where available.
        Channel("emmanuel-tv", "Emmanuel TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://emmanuel.tv/", true),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://loveworldsat.org/live-tv/", true),
        Channel("good-news-tv", "Good News TV", "Gospel", "Televisão cristã internacional", "https://www.mygoodnewstv.com/live", true),
        Channel("good-news-tv-kids", "Good News TV Kids", "Havila Kids", "Conteúdo cristão para crianças", "https://www.mygoodnewstv.com/live", true),
        Channel("tbn", "TBN", "Gospel", "Conteúdo cristão, ensino e entretenimento", "https://www.tbn.org/watch", true),
        Channel("hope-channel-africa", "Hope Channel Africa", "Gospel", "Fé, família, saúde e educação", "https://hopeafrica.tv/ways-to-watch", true),
        Channel("faith-tv", "Faith TV", "Gospel", "Programação cristã e familiar", "https://africa.myfaith.tv/", true),
        Channel("cmtv", "CMTV", "Gospel", "Conteúdo cristão africano e familiar", "https://www.cmtvfoundation.com/", true),
        Channel("eternal-life-tv", "Eternal Life TV", "Gospel", "Programação cristã e transmissão ao vivo", "https://eternallifetv.com/", true),

        // Satellite/FTA catalogue: kept separate from online-live destinations until a licensed online stream is confirmed.
        Channel("christ-tv", "Christ TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false),
        Channel("tz-gospel-tv", "TZ Gospel TV", "Gospel", "Canal Gospel — disponibilidade FTA por satélite", null, false),
        Channel("heaven-channel", "Heaven Channel", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false),
        Channel("king-james-tv", "King James TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false),
        Channel("glorious-times-tv", "Glorious Times TV", "Gospel", "Canal cristão — disponibilidade FTA por satélite", null, false),
        Channel("hope-channel-malawi", "Hope Channel Malawi", "Gospel", "Canal cristão — disponibilidade por satélite", null, false),
        Channel("hope-channel-tanzania", "Hope Channel Tanzania", "Gospel", "Canal cristão — disponibilidade por satélite", null, false),
        Channel("startimes-gospel", "StarTimes Gospel", "Gospel", "Canal Gospel — disponibilidade por plataforma/satélite", null, false),
        Channel("kln-tv", "KLN TV", "Gospel", "Canal cristão — disponibilidade por plataforma/satélite", null, false),

        // News and international destinations.
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", "https://www.rtp.pt/play/direto/rtpafrica", true),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", "https://www.africanews.com/live/", true),
        Channel("dw", "DW", "Notícias", "Informação internacional", "https://www.dw.com/en/live-tv/s-100825", true),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", "https://www.france24.com/en/live", true),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", "https://www.euronews.com/live", true),
        Channel("avo-tv", "AVO TV", "Entretenimento", "Canais FAST gratuitos em África", "https://www.avo.tv/", true),
        Channel("twale-tv", "Twale TV", "Entretenimento", "Filmes, música, desporto, família e fé", "https://www.twale.app/", true),
        Channel("tugo-tv", "Tugo TV", "Família", "Família, notícias e conteúdos baseados na fé", "https://www.tugotv.com/", true)
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
