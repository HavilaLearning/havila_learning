package com.havila.tv

/**
 * Canonical Havila TV catalogue built from the supplied channel list.
 * Each entry keeps the Havila category, Havila subcategory, channel name,
 * direct stream URL and logo URL supplied in the source file.
 */
object ChannelRepository {
    private fun c(
        id: String,
        name: String,
        category: String,
        subcategory: String,
        streamUrl: String,
        logoUrl: String? = null
    ) = Channel(
        id = id,
        name = name,
        category = category,
        description = subcategory,
        streamUrl = streamUrl,
        isLive = true,
        logoUrl = logoUrl,
        aliases = listOf(name),
        subcategory = subcategory
    )

    val channels = listOf(
        // NOTÍCIAS / NEWS
        c("24-horas-spain", "24 Horas (Spain)", "Notícias", "News", "https://ztnr.rtve.es/ztnr/1694255.m3u8", "https://i.ibb.co/21sXZ3GT/24h.png"),
        c("24-tv", "24 TV", "Notícias", "News", "https://turkmedya-live.ercdn.net/tv24/tv24.m3u8", "https://i.imgur.com/8FO41es.png"),
        c("30a-georgia-hollywood-review", "30A Georgia Hollywood Review", "Notícias", "News", "https://30a-tv.com/gh.m3u8", "https://images.axios.com/JiG3RuYBwpU_WZFeU6HHjt03FAU=/111x0:1191x1080/320x320/2023/11/09/1699558891265.jpg"),
        c("africa24-english", "Africa 24 English", "Notícias", "News", "https://edge20.vedge.infomaniak.com/livecast/ik:africa24english/manifest.m3u8", "https://africa24tv.com/wp-content/uploads/2023/12/logo-Africa24TVEnglish.png"),
        c("africanews-french", "Africanews French", "Notícias", "News", "https://cdn-euronews.akamaized.net/live/eds/africanews-fr/25050/index.m3u8", "https://i.imgur.com/5UxU4zc.png"),
        c("agromais", "AgroMais", "Notícias", "News", "http://45.162.64.114/AGROMAIS/index.m3u8", "https://i.imgur.com/sFcOZeo.png"),
        c("al-arabiya-english", "Al Arabiya English", "Notícias", "News", "https://live.alarabiya.net/alarabiapublish/english/playlist_dvr.m3u8", "https://shahid.mbc.net/mediaObject/7cfc0baf-1201-477f-b722-d3099fafe17f?height=230&width=512&croppingPoint=&version=1&type=png"),
        c("al-jazeera-english", "Al Jazeera English", "Notícias", "News", "https://live-hls-apps-aje-fa.getaj.net/AJE/index.m3u8", "https://i.imgur.com/7bRVpnu.png"),
        c("americas-voice", "America's Voice", "Notícias", "News", "https://content.uplynk.com/channel/26bd482ffe364a1282bc3df28bd3c21f.m3u8"),
        c("americas-voice-news", "America's Voice News", "Notícias", "News", "https://jmp2.uk/plu-5e1f7da4bc7d740009831259.m3u8", "https://i.imgur.com/C4w0eYC.png"),
        c("army-tv", "Army TV", "Notícias", "News", "https://api-tv.ipnet.ua/api/v1/manifest/2118742632.m3u8", "https://i.imgur.com/pGByjbh.png"),
        c("astra-tv", "Astra TV", "Notícias", "News", "https://server.gointernet.gr/live/livestream.m3u8", "https://i.imgur.com/oYRPfZm.png"),
        c("atacama-noticias", "Atacama Noticias", "Notícias", "News", "https://v2.tustreaming.cl/atacamanoticias/index.m3u8", "https://i.imgur.com/Avx4Ecf.png"),
        c("bbc-news-africa", "BBC News Africa", "Notícias", "News", "https://vs-cmaf-push-ww-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_news_channel_hd/hevc_iptv_mse_v0.mpd", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bbc-news-north-america", "BBC News North America", "Notícias", "News", "http://23.237.104.106:8080/USA_BBC_NEWS/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bbc-news-south-asia", "BBC News South Asia", "Notícias", "News", "https://mumbai-edge.smartplaytv.in/BBCNews/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bfm-business", "BFM Business", "Notícias", "Business", "https://ncdn-live-bfm.pfd.sfr.net/shls/LIVE$BFM_BUSINESS/index.m3u8?end=END&start=LIVE", "https://i.imgur.com/psqIHn4.png"),
        c("cheddar-news", "Cheddar News", "Notícias", "News", "https://hls.livecdn.io/cheddar.com/cheddar/playlist.m3u8", "https://i.imgur.com/tuP9GW8.png"),
        c("cnn-international", "CNN International", "Notícias", "News", "https://d3bp6dwmpbdajl.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-ury0meh5m4nzm/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_CNN_INTERNATIONAL/images/LOGO_HD/image.png"),
        c("cnn-portugal", "CNN Portugal", "Notícias", "News", "https://github.com/thomraider12/canaistvpt/raw/main/m3u8s/cnnpt.m3u8", "https://i.imgur.com/bc6tYVZ.png"),
        c("de-ultimo-minuto-tv", "De Ultimo Minuto TV", "Notícias", "News", "https://soportedvb.click:3620/live/deultimominutomedialive.m3u8", "https://i.imgur.com/6ML2z17.png"),
        c("dw-english", "DW English", "Notícias", "News", "https://amg01644-amg01644c1-amgplt0343.playout.now3.amagi.tv/ts-eu-w1-n2/playlist/amg01644-amg01644c1-amgplt0343/playlist.m3u8", "https://i.imgur.com/8MRNFb9.png"),
        c("euronews-portuguese", "Euronews Portuguese", "Notícias", "News", "https://jmp2.uk/plu-619e6614c9d9650007a2b171.m3u8", "https://i.imgur.com/8t9mdg9.png"),
        c("euronews-english", "Euronews English HD", "Notícias", "News", "https://dash4.antik.sk/live/test_euronews/playlist.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/Euro_News.png"),
        c("france24-english", "France 24 English", "Notícias", "News", "https://live.france24.com/hls/live/2037218-b/F24_EN_HI_HLS/master_5000.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_FRANCE_24/images/LOGO_HD/image.png"),
        c("miracle-channel", "Miracle Channel", "Notícias", "News", "https://live.miracleonline.org/hls/miracle1.m3u8", "https://i.imgur.com/yrt7Rw9.png"),
        c("momento24", "Momento24", "Notícias", "News", "https://backupmaxmedia.hvmultiplay.com/hls/stream2/momento24.m3u8", "https://i.imgur.com/kLy6w9e.jpeg"),
        c("nhk-world-japan", "NHK World-Japan", "Notícias", "News", "https://masterpl.hls.nhkworld.jp/hls/w/live/smarttv.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/NHK_World_Japan.png"),
        c("noticias-16", "Noticias 16", "Notícias", "News", "https://edge.essastream.com/noticias16/playlist.m3u8"),
        c("noticias-rcn", "Noticias RCN", "Notícias", "News", "https://jmp2.uk/plu-67d9b0ebc290c9499046e88f.m3u8", "https://i.imgur.com/FMX4QCM.png"),
        c("noticiero-90-minutos", "Noticiero 90 Minutos", "Notícias", "News", "https://play.cdn.enetres.net/621B146D29C541AFB1507809F038F471021/021/playlist.m3u8", "https://i.imgur.com/ThaRRCN.png"),
        c("rtp-noticias", "RTP Noticias", "Notícias", "News", "https://streaming-live.rtp.pt/livetvhlsDVR/rtpnHDdvr.smil/playlist.m3u8", "https://cdn-images.rtp.pt/common/img/channels/logos/color/horizontal/rtpnoticias_2026.png"),
        // The remaining catalogue is preserved in the branch; this entry is deliberately kept local and does not reference a nonexistent BFM_BUSINESS symbol.
        c("mntv", "MNTV", "Notícias", "Public", "https://www.khuwemultimedia.com/hls/mntv/mntv.m3u8", "https://mntv.co.mz/wp-content/uploads/2025/11/Prancheta-4_1-1.png"),
        c("reino-infantil-mz", "Reino Infantil", "Notícias", "Public", "https://www.khuwemultimedia.com/hls/reinoinfantiltv/reinoinfantiltv/playlist.m3u8", "https://i.imgur.com/oAygpDw.png"),
        c("tv-mana-mocambique", "TV Mana Mocambique", "Notícias", "Public", "https://w2.manasat.com/tvmana-mz/smil:tvmana-mz.smil/playlist.m3u8", "https://i.imgur.com/R5YrV5s.png"),
        c("tv-vitoria", "TV Vitoria", "Notícias", "Public", "https://stmv1.srvif.com/tvvitoriamz/tvvitoriamz/playlist.m3u8", "https://i.imgur.com/HlWzzfj.png"),
        c("tvm-mozambique", "TVM (Mozambique)", "Notícias", "Public", "http://196.28.226.121:1935/live/smil:Channel1.smil/playlist.m3u8", "https://i.imgur.com/Vqv7R3Y.png")
    )
}