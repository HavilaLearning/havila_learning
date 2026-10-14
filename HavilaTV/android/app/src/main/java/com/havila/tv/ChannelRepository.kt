package com.havila.tv

/** Initial catalogue of free/public channel destinations. */
object ChannelRepository {
    val channels = listOf(
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", "https://mbc.co.mz/tv-ao-vivo/", true),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", null, false),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false),
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
