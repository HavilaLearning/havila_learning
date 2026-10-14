package com.havila.tv

object ChannelRepository {
    val channels = listOf(
        Channel("news-demo", "Havila News", "Notícias", "Notícias e atualidade", null, true),
        Channel("gospel-demo", "Havila Gospel", "Gospel", "Fé, esperança e inspiração", null, true),
        Channel("sport-demo", "Havila Desporto", "Desporto", "Notícias e análise desportiva", null, true)
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
}
