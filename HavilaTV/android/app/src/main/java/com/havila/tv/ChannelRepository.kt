package com.havila.tv

/**
 * Initial Havila TV free-channel catalogue.
 *
 * The catalogue is intentionally separated from stream URLs: only official,
 * publicly authorized stream endpoints should be added when confirmed.
 */
object ChannelRepository {
    val channels = listOf(
        Channel("mbc-mozambique", "MBC Moçambique", "Moçambique", "Notícias, informação e atualidade", null, false),
        Channel("tv-mana", "TV Mana Moçambique", "Gospel", "Conteúdo cristão e inspiração", null, false),
        Channel("reino-infantil", "Reino Infantil", "Havila Kids", "Conteúdo infantil e educativo", null, false),
        Channel("tvm", "TVM", "Moçambique", "Televisão pública moçambicana", null, false),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", null, false),
        Channel("tv-vitoria", "TV Vitória", "Entretenimento", "Entretenimento e programação familiar", null, false),
        Channel("rtp-africa", "RTP África", "Notícias", "Informação e conteúdos de Portugal e África", null, false),
        Channel("africanews", "Africanews", "Notícias", "Notícias de África e do mundo", null, false),
        Channel("dw", "DW", "Notícias", "Informação internacional", null, false),
        Channel("france24", "France 24", "Notícias", "Notícias internacionais", null, false),
        Channel("euronews", "Euronews", "Notícias", "Notícias da Europa e do mundo", null, false),
        Channel("avo-tv", "AVO TV", "Entretenimento", "Canais FAST gratuitos em África", null, false),
        Channel("twale-tv", "Twale TV", "Entretenimento", "Filmes, música, desporto, família e fé", null, false),
        Channel("tugo-tv", "Tugo TV", "Família", "Família, notícias e conteúdos baseados na fé", null, false)
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
